#include <stdio.h>
#include <stdlib.h>

// Code provided by Dr. Sean Szumlanski
typedef struct heap
{
	// Create a pointer to an array to hold elements
	int *array;

	// number of elements in heap
	int size;

	// max size of heap
	int capacity;
} heap;

// Code provided by Dr. Sean Szumlanski
heap *makeHeap(int n)
{
	// Create a heap with the appropriate size
	heap *h = malloc(sizeof(heap));

	// Allocate the correct amount of memory for the array
	h->array = malloc(sizeof(int) * n);

	// Initialize the number of elements in the array
	h->size = 0;

	// Initialize the max amount of elments allowed
	h->capacity = n;

	return h;
}

// Code provided by Dr. Sean Szumlanski
void swap(int *array, int i, int j)
{
	// Swap values
	int tmp = array[i]; array[i] = array[j]; array[j] = tmp;
}

// Code provided by Dr. Sean Szumlanski
void heapInsert(heap *h, int key)
{
	// List variables
	int child, parent;

	// If the heap has no more space return
	if (h->size >= h->capacity)
		return;

	// Set key to the index of h->array
	h->array[h->size++] = key;

	// After setting key, subtract size and percolate up
	child = h->size - 1;
	parent = (child - 1) / 2;

	while (h->array[child] < h->array[parent])
	{
		// Swap child and parent
		swap(h->array, child, parent);

		// Set new position after percolattion
		child = parent;

		// Find the next elements to swap
		parent = (child - 1) / 2;
	}
}

// Code provided by Dr. Sean Szumlanski
int minChildIndex(heap *h, int parent)
{
	// List variables
	int lc, rc;

	// Find which way to travel depending on lc and rc
	lc = (parent * 2 + 1 >= h->size) ? parent : (parent * 2 + 1);
	rc = (parent * 2 + 2 >= h->size) ? parent : (parent * 2 + 2);

	// Return lc if less than or equal to right child
	return (h->array[lc] <= h->array[rc]) ? lc : rc;
}

// Code provided by Dr. Sean Szumlanski
int deleteMin(heap *h)
{
	// List variables
	int ret, child, parent;

	// If there isn't anything in the heap return
	if (h->size <= 0)
		return 0;

	// Return the value of the root
	ret = h->array[0];

	// Return when the heap is empty
	if (--h->size == 0)
		return ret;

	// Move the very last element up to the root
	h->array[0] = h->array[h->size];

	// Then percolate down
	parent = 0;
	child = minChildIndex(h, 0);

	while (h->array[parent] > h->array[child])
	{
		// Use swap function to swap parent and child
		swap(h->array, parent, child);

		// Elements are now in position
		parent = child;

		// Find next positions to swap
		child = minChildIndex(h, parent);
	}

	return ret;
}

void destroyHeap(heap *h)
{
	// First free the array inside the heap
  free(h->array);

	// Then free the actual heap
  free(h);
}

int add(heap *h, int nums)
{
	// List variables and initiliaze
	int s_array[nums];
	int sum_array[nums-1];
	int s = 0;
	int final_sum = 0;
	int temp;
	int a,b,c,i,l;

	// If there is only one element it doesn't cost anything to add
	if(nums == 1)
	  return 0;

	// If there are only two elements in the array just return the sum
	else if(nums == 2)
	  return h->array[0] + h->array[1];

	// For all arrays greater than 2
	else
	{
		// Loop through script one less times than the numbers in the array
		for(a = 0; a < nums-1; a++)
		{
			// Sort the heap array using deleteMin and set in static array
			for(i = 0; i < nums; i++)
				s_array[i] = deleteMin(h);

			// Initiliaze index variables
			int k = 0;
			int j = 1;

			// Loop through static array
			for(l = 0; l < nums; l++)
			{

				// If static array = 0, we skip that position
				if(s_array[l] == 0)
				{
					k++;
					j++;
				}

				// Else, we get the sum and set it to a temp variables
				else
		 			temp = s_array[k] + s_array[j];
			}

			// Set the last positon of k to 0 and the last position of j to temp
			s_array[k] = 0;
			s_array[j] = temp;

			// Add temp to sum array and increment index
			sum_array[s] = temp;
			s++;

			// Insert all values back into heap array
			for(b = 0 ; b < nums; b++)
				heapInsert(h, s_array[b]);
		}

		// At add all values inside sum array to get the total, minimum cost
		for(c = 0; c < nums-1; c++)
			final_sum+=sum_array[c];
	}
	// Return value
	return final_sum;
}

int main(void)
{
  // List Variables
  int test_cases, i;
  int num_of_nums, j;
  int k;
  int data;
	int main_add;
	heap *main_heap;

  // Scan in number of test cases
  scanf("%d", &test_cases);

  for(i = 0; i < test_cases; i++)
  {
    // Scan in number of numbers to insert
    scanf("%d", &num_of_nums);

    // Create heap
    main_heap = makeHeap(num_of_nums);

    // Insert data into heap
    for(j = 0; j < num_of_nums; j++)
    {
      scanf("%d", &data);
      heapInsert(main_heap,data);
    }

    // Print out cost
		main_add = add(main_heap, num_of_nums);
		printf("%d\n", main_add);

		// Free heap
    destroyHeap(main_heap);
  }
  return 0;
}
