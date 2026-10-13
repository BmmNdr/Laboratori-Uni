# Formulazione Problema
# min 3x_1 + 6x_2 + 5x_3 + 6x_4
# Vincoli:
#   x_1 + 3x_2 + 2x_3 + 3x_4 >= 2
#   4x_1 + 4x_2 + 3x_3 + 2x_4 >= 4
#   3x_1 + 2x_2 + 3x_3 + 4x_4 >= 3
#   x_1, x_2, x_3, x_4 >= 0

# sudo pacman -S glpk
import pyomo.environ as pyo

# Software che effettua l'ottimizzazione
SOLVER = pyo.SolverFactory('glpk')
# Definizione di una struttura dati modello (in questo caso un modello concreto)
model = pyo.ConcreteModel("Dieta")

# Variabili di Decisione
model.x_1 = pyo.Var(domain=pyo.NonNegativeReals)
model.x_2 = pyo.Var(domain=pyo.NonNegativeReals)
model.x_3 = pyo.Var(domain=pyo.NonNegativeReals)
model.x_4 = pyo.Var(domain=pyo.NonNegativeReals)

# Funzione Obiettivo
model.dieta = pyo.Objective(expr=3 * model.x_1 + 6 * model.x_2 + 5 * model.x_3 + 6 * model.x_4, sense=pyo.minimize) 

# Vincoli
model.proteine = pyo.Constraint(expr=model.x_1 + 3 * model.x_2 + 2 + model.x_3 + 3 * model.x_4 >= 2)
model.carboidrati = pyo.Constraint(expr=4 * model.x_1 + 4 * model.x_2 + 3 * model.x_3 + 2 * model.x_4 >= 4)
model.grassi = pyo.Constraint(expr=3 * model.x_1 + 2 * model.x_2 + 3 * model.x_3 + 4 * model.x_4 >= 3)


# Inizia la risoluzione e stampa i risultati
SOLVER.solve(model)
print(f"x = ({model.x_1.value:.1f}, {model.x_2.value:.1f}, {model.x_3.value:.1f}, {model.x_4.value:.1f})")
print(f"optimal value = {pyo.value(model.dieta):.1f}")