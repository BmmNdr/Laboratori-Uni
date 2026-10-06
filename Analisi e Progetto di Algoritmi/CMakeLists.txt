cmake_minimum_required(VERSION 3.20)

project(algorithms LANGUAGES CXX)

set(CMAKE_CXX_STANDARD 23)
set(CMAKE_CXX_STANDARD_REQUIRED ON)
set(CMAKE_CXX_EXTENSIONS OFF)

add_executable(lcs
    Programmazione_dinamica/lcs.cpp
)

add_executable(wis
    Programmazione_dinamica/wis.cpp
)