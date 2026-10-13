# Formulazione Problema
# x >= 0
# y >= 0
# max ?x + ?y   => Supponiamo di avere profitti incerti
# max z
#   z <= 12x + 9y   # Previsioni sul profitto e sui prezzi
#   z <= 11x + 10y  # Vogliamo mazzimizzare il profitto
#   z <= 8x + 11y   # Nel caso peggiore di prezzi
#   x <= 1000
#   y <= 1500
#   x + y <= 1750
#   4x + 2y <= 4800

# sudo pacman -S glpk
import pyomo.environ as pyo

# Software che effettua l'ottimizzazione
SOLVER = pyo.SolverFactory('glpk')
model = pyo.ConcreteModel("BIM production planning | uncertain")

# Variabili di Decisione
model.x1 = pyo.Var(domain=pyo.NonNegativeReals)
model.x2 = pyo.Var(domain=pyo.NonNegativeReals)
model.z = pyo.Var(domain=pyo.NonNegativeReals)

# Funzione Obiettivo
model.profit = pyo.Objective(expr=model.z, sense=pyo.maximize) 

# Vincoli Uncertainty (Previsioni)
model.prev1 = pyo.Constraint(expr=model.z <= 12 * model.x1 + 9 * model.x2)
model.prev2 = pyo.Constraint(expr=model.z <= 11 * model.x1 + 10 * model.x2)
model.prev3 = pyo.Constraint(expr=model.z <= 8 * model.x1 + 11 * model.x2)

# Vincoli
model.silicon = pyo.Constraint(expr=model.x1 <= 1000)
model.germanium = pyo.Constraint(expr=model.x2 <= 1500)
model.plastic = pyo.Constraint(expr=model.x1 + model.x2 <= 1750)
model.copper = pyo.Constraint(expr=4 * model.x1 + 2 * model.x2 <= 4800)

# Risoluzione
SOLVER.solve(model)
print(f"x = ({model.x1.value:.1f}, {model.x2.value:.1f})")
print(f"z = ({model.z.value:.1f})")
print(f"optimal value = {pyo.value(model.profit):.1f}")