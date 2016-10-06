// Alec Dilanchian

#include <iostream>

struct node {
  int data;
  node *next;
};

//-- Node Helpers --//
node *createNode(int value) {
  // Create new node
  node *temp = new node;
  
  // Set data on node
  temp->data = value;
  temp->next = NULL;
  
  return temp;
};

node *insertNode(node *head, int value) {
  // Create a temp pointer
  node *temp;
  
  // Check to see if head is null
  if (head == NULL)
    return NULL;
    
  // Find where the 'next' value is NULL
  for (temp = head; temp->next != NULL; temp = temp->next);
  
  // Set data on temp
  temp->next = createNode(value);
  
  return head;
}

void printList(node *head) {
  node *temp;
  
  for (temp = head; temp->next != NULL; temp = temp->next) {
    printf("Address of current node: %p\n", temp);
    printf("Data of current node: %d\n\n", temp->data);
    printf("Next address of current node: %p\n\n", temp->next);
  }
}

int main() {
  // Create head node
  node *head = createNode(20);
  
  // Insert nodes 
  for (int i = 0; i < 20; i++) 
    insertNode(head, i);
  
  // Print list of nodes
  printList(head);
  return 0;
}