/*
Alec Dilanchian
al547100
COP 3502
Assignnemt #3
*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "ListyString.h"
#define MAX_CHARS 1023

// Create Node function
// Code provided by Dr. Sean Szumlanski
node *createNode(char str)
{
  // Create memory space for node
  node *temp = malloc(sizeof(node));

  if (temp == NULL)
    return NULL;
  // Set data in node
  temp->data = str;
  temp->next = NULL;

  // Return node
  return temp;
}

// Add node to list
// Code provided by Dr. Sean Szumlanski
node *insertNode(node *head, char str)
{
  // Create a temp node
  node *temp;

  // If no nodes in list, create node
  if (head == NULL)
		return createNode(str);

  // Move head forward until NULL is hit to create new node
  for (temp = head; temp->next != NULL; temp = temp->next)
  		;

  // Set pointer to new node address after creating node
  temp->next = createNode(str);

  // Return new head of node to main
  return head;
}

// Convert string to Link List
node *stringToList(char *str)
{
  // Declare variables
  int i;
  int count;
  node *head = NULL;

  // If string is NULL or empty, return NULL
  if(str == '\0' || str == NULL)
    return NULL;

  // Get length of string from main
  count = strlen(str);

  // Loop through each char to begin link list creation
  for(i = 0; i < count; i++)
    // Set head to returned of insertNode
    head = insertNode(head, str[i]);

  // Return head to main
  return head;
}

//Print List when "!" is called
// Code provided by Dr. Sean Szumlanski
void printList(node *head)
{
  // When end of list is hit return and break line
  if (head == NULL)
  {
    printf("\n");
    return;
  }
  // Print data of linked list
	printf("%c", head->data);
	printList(head->next);
}

// Reverse List when "~" is called
node *reverseList(node *head)
{
  // Declare variables
  node *temp_head;
  node *nullifier = NULL;

  // Loop through linked list until NULL is hit
  while(head != NULL)
  {
    // Get address of next node
    temp_head = head->next;

    // Set position of pointer to previous address
    head->next = nullifier;

    // Set nullifier to next node in linked list
    nullifier = head;

    // Move to the next node in list
    head = temp_head;
  }
  // Return value of node to main
  return nullifier;
}

// Delete character key when "-" is called
node *deleteChar(node *head, char key)
{
  // List variables
  node *temp;
  node *loop;

  // First loop through
  // Delete any instance of key
  while(head != NULL && head->data == key)
  {
    // Set head to temp variable to free that address
    temp = head;

    // Delete node
    free(temp);

    // Navigate head to next node
    head = head->next;
  }

  // Second loop through, catch any repeating keys
  for(loop = head; loop != NULL; loop = loop->next)
  {
    // Only go into this loop when pointer is NULL and the data of that pointer
    // is equal to key
    while(loop->next != NULL && loop->next->data == key)
    {
      // Set temp to next node head
      temp = loop->next;

      // Set head to node after temp variable
      loop->next = temp->next;

      // Free head
      free(temp);
    }
  }
  // Return head back to main
  return head;
}

// Replace key with str when "@" is called
node *replaceChar(node *head, char key, char *str)
{
  // List variables
  int i;
  int count;
  node *temp;
  node *new_temp;

  // If string is NULL or an empty String, send to deleteChar function to delete
  if(str == NULL || str == '\0')
    deleteChar(head, key);

  // Initiliaze count with string length
  count = strlen(str);

  // Begin loop to find keys to swap
  for ( ; head != NULL; head = head->next)
  {
    if(key == head->data)
    {
      // If string length is one, just change the data of node
      if(count == 1)
        head->data = str[0];

      // If count is greater than one...
      else if(count > 1)
      {
        // Replace first node data with key
        head->data = str[0];

        // Then, loop through to insert new node after changed node
        for(i = 1; i < count; i++)
        {
          // Set temp to head pointer
          temp = head->next;

          // Return new head after creating new node
          new_temp = createNode(str[i]);

          // Set new node to pointer of head
          head->next = new_temp;

          // Set pointer of newly created node to the origianl head
          new_temp->next = temp;

          // Set new head to next node
          head = new_temp;
        }
      }
    }
  }
  // Return head
  return head;
}

// Free allocated memory
void destroyList(node *head)
{
  if (head == NULL)
		return;

	destroyList(head->next);
  free(head);
}

// Main Function
int main(int argc, char **argv)
{
    // List Variables
    int count;
    int i;
    char *file_name;
    char *main_string;
    char *temp;
    char character;
    char *replace_string;
    char main_key;
    node *main_head;

    // Get file name
    file_name = argv[1];

    //Open file
    FILE *file;
    file = fopen(file_name,"r");

  // Allocate memory for strings
  main_string = malloc(sizeof(char) * (MAX_CHARS+1));
  replace_string = malloc(sizeof(char) * (MAX_CHARS+1));

  // Read in String
  fscanf(file, "%s", main_string);

  // Send string to stringToList to convert to linked list
  main_head = stringToList(main_string);

  // Read in commands
  while(!(feof(file)))
  {
    // Scan in chars for operations
    fscanf(file, "%c", &character);

      // If scan equals break line or ' ', continue
      if(character == '\n' || character == ' ')
        continue;

    // If "@" call replaceChar
    if(character == '@')
    {
      // Scan in key to be found
      fscanf(file, "%c", &main_key);

      // If key equals ' ' scan once more
      if(main_key == ' ')
        fscanf(file, "%c", &main_key);

      // Scan in string to replace key
      fscanf(file, "%s", replace_string);

      // Call replaceChar to do that fanciness
      replaceChar(main_head, main_key, replace_string);
    }

    // If "-" call deleteChar
    else if(character == '-')
    {
      // Scan in key to delete
      fscanf(file, "%c", &character);

      // If character equals break line or ' ', scan again
      if(character == '\n' || character == ' ')
        fscanf(file, "%c", &character);

      // Call deleteChar and return head back to main_head
      main_head = deleteChar(main_head, character);
    }

    // If "~" call reverseList
    else if(character == '~')
    {
      // Return head from function back to main_head
      main_head = reverseList(main_head);
    }

    // If "!" call printList
    else if(character == '!')
    {
      // If head of list is NULL the string is empty
      if(main_head == NULL)
        printf("(empty string)\n");

      // If there is a linked list, print it
      else
        printList(main_head);
    }
  }
  // Close file
  fclose(file);

  // Free allocated memory
  free(main_string);
  free(replace_string);
  destroyList(main_head);

  // Return 0 to main
  return 0;
}
