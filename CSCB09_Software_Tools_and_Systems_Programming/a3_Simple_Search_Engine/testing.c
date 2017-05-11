#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <dirent.h>

int main() {
	char current_word[10];
	memset(current_word, '\0', 9);
	int num_bytes;
	char new = '\n';
	while ((num_bytes = read(STDIN_FILENO, current_word, 10)) > 0) {
		printf("num_bytes is %d\n", num_bytes);
		if (current_word[num_bytes-2] == new) {
			printf("%s\n", "bo");
		}
		printf("1.current_word is %s", current_word);
		current_word[num_bytes - 1] = '\0';
	    printf("2.current_word is %s\n", current_word);
	}
	return 0;
}

