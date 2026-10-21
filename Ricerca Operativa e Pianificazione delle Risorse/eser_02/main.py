import pandas as pd
import pyomo.environ as pyo

# Input parameters
df= pd.read_excel("C:/Users/lulli/dataLSP.xlsx")
demand = df['demand'].tolist()
nTime = len(demand)
timeIndex = range(0,nTime)
invInitial = 0 # initial inventory at the beginning
setupCost = df['setup'][0]
holdingCost = df['inventory'][0]
unitCost = df['unit'][0]


SOLVER = pyo.SolverFactory('glpk')
# Definizione di una struttura dati modello
model = pyo.ConcreteModel("Magazzino")

# Variabili di Decisione
for i in range(1,nTime):
    model.x[i] = pyo.Var(domain=pyo.NonNegativeReals)

# Funzione obiettivo
model.profit = pyo.Objective(expr=, sense=pyo.minimize) 

# vincoli 
model.demand = pyo.Constraint(expr=)
model.order = pyo.Constraint(expr=)

SOLVER.solve(model)
print(f"x = ({model.x1.value:.1f}, {model.x2.value:.1f})")
print(f"optimal value = {pyo.value(model.profit):.1f}")