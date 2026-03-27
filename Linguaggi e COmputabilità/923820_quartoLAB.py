#!/usr/bin/env python3
from lark import Lark, Tree, Token

# ---------------------------------------------------
# Valuta ricorsivamente il valore delle espressioni
# ----------------------------------------------------
def evaluate_exp(node):
    # Caso 1: E' un Token (SIGNED_NUMBER o IDENTIFIER)
    if isinstance(node, Token):
        if node.type == 'SIGNED_NUMBER':
            return int(node.value)
        if node.type == 'IDENTIFIER':
            return REGISTERS[node.value]

    # Caso 2: E' un Nodo Tree
    children = node.children

    # ... con un solo figlio
    if len(children) == 1:
        return evaluate_exp(children[0])

    # ... con due figli
    if len(children) == 2:
        left = evaluate_exp(children[0])
        right = evaluate_exp(children[1])
        if node.data == 'sub': return left - right
        if node.data == 'minore' : return left < right
        if node.data == 'uguale' : return left == right
        if node.data == 'maggiore' : return left > right
        if node.data == 'sum' : return left + right
        if node.data == 'div' : return left / right
        if node.data == 'mul' : return left * right

# ------------------------------------------------------------------------------------------
# Funzione ricorsiva che esplora l'albero sintattico e esegue le istruzioni del programma
# ------------------------------------------------------------------------------------------
def interpret(node):
    children = node.children

    if node.data == 'start':
        interpret(children[0])

    elif node.data == 'command_s':
        # Eseguire la sequenza di comandi
        for child in children:
            interpret(child)

    elif node.data == 'cmd_read':
        # chiede di inserire un numero intero e lo assegna al registro argomento di read
        reg_name = children[0].value
        value = int(input("Inserisci un numero intero: "))
        REGISTERS[reg_name] = value

    elif node.data == 'cmd_write':
        # Stampa il risultato della valutazione dell'espressione argomento di write
        result = evaluate_exp(children[0])
        print("Output: " + str(result))

    elif node.data == 'cmd_assign':
        reg_name = children[0].value
        value = evaluate_exp(children[1])
        REGISTERS[reg_name] = value

    elif node.data == 'cmd_if':
        condition = evaluate_exp(children[0])
        if condition:
            interpret(children[1])
        else:
            interpret(children[2])

    elif node.data == 'cmd_skip':
        return

    elif node.data == 'cmd_while':
        condition = evaluate_exp(children[0])
        while condition:
            interpret(children[1])
            condition = evaluate_exp(children[0])

    return



# -------------------------------------
# GRAMMATICA SIMPLE
# -------------------------------------
gramm = """
start: "BEGIN" command_s "END"

// Permette sequenze di comandi (command; command; command;)
command_s: (command ";")+

command:
    | "skip"                                          -> cmd_skip
    | "read" IDENTIFIER                               -> cmd_read
    | "write" exp                                     -> cmd_write
    | IDENTIFIER ":=" exp                             -> cmd_assign
    | "if" exp "then" command_s "else" command_s "fi" -> cmd_if
    | "while" exp "do" command_s "done"               -> cmd_while

// Regole di Espressione
exp:
    | exp "=" exp             -> uguale
    | exp "<" exp             -> minore
    | exp ">" exp             -> maggiore
    | exp "-" exp             -> sub
    | exp "+" exp             -> sum
    | exp "*" exp             -> mul
    | exp "/" exp             -> div
    | "(" exp ")"
    | SIGNED_NUMBER
    | IDENTIFIER

// Terminali
IDENTIFIER: "R" /[0-9]/

%import common.SIGNED_NUMBER
%import common.WS
%ignore WS

%ignore /\/\/.*?\n/ // Ignora i commenti
"""

# ------------------------------
# PROGRAMMA SIMPLE DA ESEGUIRE
# ------------------------------
programma = """
BEGIN
    read R1;
    if ( R1 < 10 ) then
        R2 := R1;
    else
        R2 := R1 - 5;
    fi;
    write R2;

    read R1;
    while R8 < (R1 + 2) do
        R8 := R8 + 1;
        write R8;
    done;
END
"""

# implementa la memoria del programma
REGISTERS = {'R0' : 0, 'R1' : 0, 'R2' : 0, 'R3' : 0, 'R4' : 0, 'R5' : 0, 'R6' : 0, 'R7' : 0, 'R8' : 0, 'R9' : 0,}

parser = Lark(gramm, start='start')
pars_tree = parser.parse(programma)
interpret(pars_tree)

print ("\n----------------------------------------------------------------------------------------------------------------------")
print(pars_tree)
print ("----------------------------------------------------------------------------------------------------------------------")
print(REGISTERS)
