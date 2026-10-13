# Formulazione Problema
# x >= 0
# y >= 0
# max 12x + 9y
#   x <= 1000
#   y <= 1500
#   x + y <= 1750
#   4x + 2y <= 4800

# sudo pacman -S glpk
import pyomo.environ as pyo

# Software che effettua l'ottimizzazione
SOLVER = pyo.SolverFactory('glpk')
# Definizione di una struttura dati modello (in questo caso un modello concreto)
model = pyo.ConcreteModel("BIM production planning")

# Inserisco le variabili di decisione nel modello, specificando il vincolo di non negatività
model.x1 = pyo.Var(domain=pyo.NonNegativeReals)
model.x2 = pyo.Var(domain=pyo.NonNegativeReals)
# Inserisco la funzione obiettivo nel modello, specificando che l'obiettivo è massimizzare.
model.profit = pyo.Objective(expr=12 * model.x1 + 9 * model.x2, sense=pyo.maximize) 
# Inserisco i vincoli nel modello
model.silicon = pyo.Constraint(expr=model.x1 <= 1000)
model.germanium = pyo.Constraint(expr=model.x2 <= 1500)
model.plastic = pyo.Constraint(expr=model.x1 + model.x2 <= 1750)
model.copper = pyo.Constraint(expr=4 * model.x1 + 2 * model.x2 <= 4800)
# Inizia la risoluzione e stampa i risultati
SOLVER.solve(model)
print(f"x = ({model.x1.value:.1f}, {model.x2.value:.1f})")
print(f"optimal value = {pyo.value(model.profit):.1f}")