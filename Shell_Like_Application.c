#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <libgen.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <readline/readline.h>
#include <fcntl.h>

//functie pentru citirea comenzii
int citireCmd(char *cmd)
{
    char *s;

    s = readline("");
    if (strlen(s) != 0)
    {
        strcpy(cmd, s);
        return 0;
    }
    else
        return 1;
}

//functie pentru executarea comenzilor citite
void execCmd(char **str_parsat)
{
    pid_t pid = fork();

    if (pid == -1)
    {
        printf("\nNu s-a putut creea un proces now prin fork()");
        return;
    }
    else if (pid == 0)
    {
        if (execvp(str_parsat[0], str_parsat) < 0)
        {
            printf("\nComanda nu s-a putut executa");
        }
        return;
    }
    else
    {
        wait(NULL);
        return;
    }
}

//functie pentru executarea comenzilor cu piping
void execCmdPiping(char **str_parsat, char **str_parsatPipe)
{
    int v[2];
    pid_t p1, p2;

    if (pipe(v) < 0)
    {
        printf("Eroare piping\n");
        return;
    }
    p1 = fork();
    if (p1 < 0)
    {
        printf("Eroare fork()\n");
        return;
    }

    if (p1 == 0)
    {
        close(v[0]);
        dup2(v[1], STDOUT_FILENO);
        close(v[1]);

        if (execvp(str_parsat[0], str_parsat) < 0)
        {
            printf("Nu s-a putut executa comanda\n");
            exit(0);
        }
    }
    else
    {

        p2 = fork();

        if (p2 < 0)
        {
            printf("Eroare fork()\n");
            return;
        }

        if (p2 == 0)
        {
            close(v[1]);
            dup2(v[0], STDIN_FILENO);
            close(v[0]);
            if (execvp(str_parsatPipe[0], str_parsatPipe) < 0)
            {
                printf("Nu s-a putut executa comanda\n");
                exit(0);
            }
        }
        else
            wait(NULL);
    }
}

//functie parsare pt piping
int parsareP(char *s, char **spiped)
{
    for (int i = 0; i < 2; i++)
    {
        spiped[i] = strsep(&s, "|");
        if (spiped[i] == NULL)
            break;
    }

    if (spiped[1] == NULL)
        return 0;
    else
        return 1;
}

//implementeaza cp -i 
void cp_i(char *source, char *destination)
{
    char buff[1024];
    int pozitie;

    int source_folder = open(source, O_RDONLY, 0666);
    int destination_folder = open(destination, O_CREAT | O_TRUNC | O_WRONLY);

    while ((pozitie = read(source_folder, &buff, 1024)))
    {
        int write_ret = write(destination_folder, &buff, pozitie);
    }
    close(source_folder);
    close(destination_folder);
}

//implementeaza cp -r
void cp_r(char *source_path, char *destination_path)
{
    int source_folder, destination_folder, n, k;
    char buff[1024];
    source_folder = open(source_path, O_RDONLY);
    destination_folder = open(destination_path, O_CREAT | O_WRONLY);

    while (1)
    {
        k = read(source_folder, buff, 1024);
        if (k == -1)
        {
            exit(1);
        }
        n = k;
        if (n == 0)
            break;
        k = write(destination_folder, buff, n);
        if (k == -1)
        {
            exit(1);
        }
    }
    close(source_folder);
    close(destination_folder);
}

//implementeaza tail -c
void tail_c(char* nr_caractere, char *filePath)
{
    int caractere = atoi(nr_caractere);
	FILE *fp;
	char* line = NULL;
	size_t len = 0;
    ssize_t read;

	fp = fopen(filePath, "r");

    if (fp == NULL)
        exit(EXIT_FAILURE);

    while ((read = getline(&line, &len, fp)) != -1) {
        fwrite(line, 1, caractere, stdout);
    }

    fclose(fp);
    if (line)
        free(line);
    exit(EXIT_SUCCESS);
}

//implementeaza tail -n
void tail_n(char* nr_linii, char *filePath)
{
	FILE *fp;
	char* line = NULL;
	size_t len = atoi(nr_linii);
    ssize_t read;

	fp = fopen(filePath, "r");

    if (fp == NULL)
        exit(EXIT_FAILURE);

    while ((read = getline(&line, &len, fp)) != -1) {
        printf("%s", line);
    }

    fclose(fp);
    if (line)
        free(line);
    exit(EXIT_SUCCESS);
}


int implementareComenzi(char **str_parsat)
{
    int nrComenzi = 4, k = 0;
    char *cmdList[nrComenzi];

    cmdList[0] = "cp -i";
    cmdList[1] = "cp -r";
    cmdList[2] = "tail -c";
    cmdList[3] = "tail -n";

    for (int i = 0; i < nrComenzi; i++)
    {
        if (strcmp(str_parsat[0], cmdList[i]) == 0)
        {
            k = i + 1;
            break;
        }
    }

    switch (k)
    {
    case 1:
        cp_i(str_parsat[1], str_parsat[2]);
        break;
    case 2:
        cp_r(str_parsat[1], str_parsat[2]);
        break;
    case 3:
    	tail_c(str_parsat[1], str_parsat[2]);
    	break;
	case 4:
		tail_n(str_parsat[1], str_parsat[2]);
        break;
    }

    return 0;
}

//functie pt a scoate spatiile inutile
void parsareSpatii(char *s, char **str_parsat)
{
    for (int i = 0; i < 4; i++)
    {
        str_parsat[i] = strsep(&s, " ");

        if (str_parsat[i] == NULL)
            break;

        if (strlen(str_parsat[i]) == 0)
            i--;
    }
}

// functie pentru procesarea stringului, dupa caz (daca este cu pipe sau fara)
int processString(char *str, char **str_parsat, char **str_parsatPipe)
{

    char* strpiped[2];
    int piped = 0;

    piped = parsareP(str, strpiped);

    if (piped)
    {
        parsareSpatii(strpiped[0], str_parsat);
        parsareSpatii(strpiped[1], str_parsatPipe);
    }
    else
        parsareSpatii(str, str_parsat);

    if (implementareComenzi(str_parsat))
        return 0;
    else
        return 1 + piped;
}

//functie pt redirectare
int redirectare(char** arg){
    int filefd = open("redir.txt", O_WRONLY|O_CREAT, 0666);
    if (!fork()) {
      close(1);
      dup2(0, filefd);
    } else {
      close(filefd);
      wait(NULL);
    }
    return 0;
}


int main()
{
    char cmd[100], *parsedArgs[5];
    int flag = 0;
    char *parsedArgsPiped[20];
    int ok = 1;

    while (ok)
    {
        if (citireCmd(cmd))
            continue;
        flag = processString(cmd, parsedArgs, parsedArgsPiped);

        if (flag == 1)
            execCmd(parsedArgs);

        if (flag == 2)
            execCmdPiping(parsedArgs, parsedArgsPiped);
        ok = 0;
    }
    redirectare(parsedArgs);

    return 0;
}