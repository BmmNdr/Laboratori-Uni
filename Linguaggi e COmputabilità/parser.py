from lark import Lark

nomefile = 'GrammaticaSimple.txt'
with open(nomefile, 'r') as file:
  gramma = file.read()

pars = Lark(gramma, start='start')

nomefile = 'Esempio.simple'
with open(nomefile, 'r') as file:
  esempio = file.read()

print(pars.parse(esempio).pretty())
