#include <stdlib.h>

#include "chunk.h"
#include "memory.h"
#include "value.h"
#include "vm.h"

void initChunk(Chunk* chunk) {
    chunk->count = 0;
    chunk->capacity = 0;
    chunk->code = NULL;
    chunk->lines = NULL;
    chunk->linesCount = 0;
    chunk->linesCapacity = 0;
    initValueArray(&chunk->constants);
}

void freeChunk(Chunk* chunk) {
    FREE_ARRAY(uint8_t, chunk->code, chunk->capacity);
    FREE_ARRAY(int, chunk->lines, chunk->linesCapacity);
    freeValueArray(&chunk->constants);
    initChunk(chunk);
}

void writeChunk(Chunk* chunk, uint8_t byte, int line) {
    if (chunk->capacity < chunk->count + 1) {
        int oldCapacity = chunk->capacity;
        chunk->capacity = GROW_CAPACITY(oldCapacity);
        chunk->code = GROW_ARRAY(uint8_t, chunk->code, oldCapacity, chunk->capacity);
    }

    chunk->code[chunk->count] = byte;
    chunk->count++;

    // Update the corresponding line counter, if found.
    for (int i = 0; i < chunk->linesCapacity; i += 2) {
        if (chunk->lines[i+1] == line) {
            // Increment the line counter.
            chunk->lines[i]++;
            return;
        }
    }
    // Corresponding line counter wasn't found, add new entry,
    // but first check if a resize is required.
    if (chunk->linesCapacity < chunk->linesCount + 2) {
        int oldCapacity = chunk->linesCapacity;
        chunk->linesCapacity = GROW_CAPACITY(oldCapacity);
        chunk->lines = GROW_ARRAY(int, chunk->lines, oldCapacity, chunk->linesCapacity);
    }
    chunk->lines[chunk->linesCount] = 1;        // line count
    chunk->lines[chunk->linesCount + 1] = line; // line number
    chunk->linesCount += 2;
}

int addConstant(Chunk* chunk, Value value) {
    push(value);
    writeValueArray(&chunk->constants, value);
    pop();
    return chunk->constants.count - 1;
}

int findConstant(Chunk* chunk, Value value) {
    // Looks for a value inside of a chunk's value array.
    // Returns the index if found and -1 otherwise.
    for (int i = 0; i < chunk->constants.count; i++) {
        Value other = chunk->constants.values[i];
        if (valuesEqual(value, other)) {
            return i;
        }
    }
    return -1;
}

int getLine(Chunk* chunk, int index) {
    // chunk->lines is an array of paired numbers, where the first
    // number represents the count and the second number represents
    // the line number.
    int runningSum = 0;
    for (int i = 0; i < chunk->linesCapacity; i += 2) {
        if (runningSum <= index && index < runningSum + chunk->lines[i]) {
            // if the index is sandwiched between the running total and
            // the next few lines, then we've found the corresponding line
            return chunk->lines[i+1];
        }
        runningSum += chunk->lines[i];
    }
    exit(1);
}