/*
Alec Dilanchian
CGS 3269
Program #3 Tiny Machine
*/

#include <stdlib.h>
#include <stdio.h>
#define MAX_MEMORY_SIZE 64

/*
  Properties Needed
  - Instruction Register
  - Program Counter (begin at 10)
  - Memory Address Register
  - Data Memory (represented by a 0-9 array)(MEM)
  - Program Memory 
  - Memory Data Register
  - Accumulator
*/

// Struct for IR && OP Code //
typedef struct {
  int opCode; 
  int deviceOrAddress;  
} Instruction;

// Tiny Machine Properties //
int programCounter = 0;
int mar = 0;
int mdr = 0;
int dataMemory[10];
int accum = 0;
Instruction mdri;
Instruction ir;

//-- Operation functions --//

// LOAD[1] //
void load(Instruction ir) {
  mar = ir.deviceOrAddress;
  mdr = dataMemory[mar];
  accum = mdr;
}

// ADD[2] //
void add(Instruction ir) {
  mar = ir.deviceOrAddress;
  mdr = dataMemory[mar];
  accum = accum + mdr;
  // end // 
}

// STORE[3] //
void store(Instruction ir) {
  mar = ir.deviceOrAddress;
  mdr = accum;
  dataMemory[mar] = mdr;
  // end //
}

// SUB[4] //
void sub(Instruction ir) {
  mar = ir.deviceOrAddress;
  mdr = dataMemory[mar];
  accum = accum - mdr;
  // end //
}

// IN[5] //
void input(Instruction ir) {
  printf("Please input a value: ");
  scanf("%d", &accum);
  // end //
}

// OUT[6] //
void out() {
  printf("%d\n", accum);
}

// JMP[8] //
void jump(Instruction ir) {
  programCounter = ir.deviceOrAddress;
  // end //
}

// SKIPZ[9] //
void skipz(Instruction ir) {
  if (accum == 0) {
    programCounter++;
  }
}

// Data Formatter - Prints out data in a friendly way //
void friendlyDataFormatter(int pc, int accumulator, int mem[]) {
  printf("[PC: %d]: A = %d | ", pc, accumulator);
  printf("MEM: [%d, %d, %d, %d, %d, %d, %d, %d, %d, %d]\n\n",
    mem[0],
    mem[1],
    mem[2],
    mem[3],
    mem[4],
    mem[5],
    mem[6],
    mem[7],
    mem[8],
    mem[9]
  );
}

// EXECUTE //
int execute(Instruction ir) {
  switch (ir.opCode) {
    // Load //
    case 1:
      load(ir);
      friendlyDataFormatter(programCounter, accum, dataMemory);
    break;
    
    // Add //
    case 2:
      add(ir);
      friendlyDataFormatter(programCounter, accum, dataMemory);
    break;
    
    // Store //
    case 3:
      store(ir);
      friendlyDataFormatter(programCounter, accum, dataMemory);
    break;
    
    // Sub //
    case 4:
      sub(ir);
      friendlyDataFormatter(programCounter, accum, dataMemory);
    break;
    
    // IN //
    case 5:
      input(ir);
      friendlyDataFormatter(programCounter, accum, dataMemory);
    break;
    
    // Out //
    case 6:
      out();
    break;
    
    // End //
    case 7:
      return -99;
    break;
    
    // Jmp //
    case 8:
      jump(ir);
      friendlyDataFormatter(programCounter, accum, dataMemory);
    break;
    
    // Skipz //
    case 9:
      skipz(ir);
      friendlyDataFormatter(programCounter, accum, dataMemory);
    break;
    default:
      printf("Invalid opCode\n");
  }
  // If opCode is not end, return 1 for main function //
  return 1;
}

int main(int argc, char *argv[]) {
  // File I/O Properties //  
  FILE *inputFile;
  char line[256];
  
  // Program Memory Array //
  Instruction programMemory[MAX_MEMORY_SIZE];
  int programMemoryIndex = 0;
    
  printf("Assembling program...\n");
  
  // See if there were args passed in //
  if (argc > 1) {
    inputFile = fopen(argv[1], "r");
  } else {
    printf("Filename not included...\n Please try again.\n");
    return 0;
  }
  
  // If file returns 0 it failed //
  if (inputFile == 0) {
    printf("Could not open file...\n");
    return 0;
  }
  
  // Test to see if file is being read //
  while (fgets(line, sizeof(line), inputFile)) {
    // Need first character && third character (because of space) //
    // Convert the chars to integers //
    int op = line[0] - '0';
    int ir = line[2] - '0';
    
    // Create Instruction struct and push into programMemory //
    Instruction newInstruction;
    newInstruction.opCode = op;
    newInstruction.deviceOrAddress = ir;
    programMemory[programMemoryIndex] = newInstruction;
    programMemoryIndex++;
  }
  
  // Close file //
  fclose(inputFile);
  
  printf("Running program...\n");
  
  // While the execute block does not == end keep running the program //
  int index = 0;
  int flag;
  while (flag != -99) {
    // Fetch ///
    mar = programCounter;
    programCounter++;
    mdri = programMemory[mar];
    ir = mdri;
    
    // Execute //
    flag = execute(ir);
    index++;
  }
  return 0;
}