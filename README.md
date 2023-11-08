#### 💯Points: ![Points bar](../../blob/badges/.github/badges/points-bar.svg)

#### 📝 [Report](../../blob/badges/report.md)
---

# SMT Solving


## Task 1: Who killed aunt Agatha?

Complete the encoding of the Dreadbury Mansion puzzle based on the formulas shown below. Use the template link below. It already contains encoding of the variables, predicates, and formula (1). Use the same names for the variables and predicates. 

**Template:** [https://play.formal-methods.net/?check=SMT&p=ex-3-task-1](https://play.formal-methods.net/?check=SMT&p=ex-3-task-1)

```math
{killed/}_{2}, {hates/}_{2}, {richer/}_{2}, {Agatha/}_{0}, {Charles/}_{0}, {Butler/}_{0}
```

$$
\begin{equation}
\begin{aligned}
  \exists x \cdot killed(x, Agatha) ... (1)  \\ 
  \forall x \cdot \forall y \cdot killed(x,y) \Rightarrow (hates(x,y)\wedge\neg richer(x,y)) ... (2) \\ 
  \forall x \cdot hates(Agatha, x) \Rightarrow \neg hates(Charles, x) ... (3) \\
  hates(Agatha, Agatha) \wedge hates(Agatha, Charles) ... (4) \\
  \forall x \cdot \neg richer(x, Agatha) \Rightarrow hates(Butler, x) ... (5) \\
  \forall x \cdot hates(Agatha, x) \Rightarrow hates(Butler, x) ... (6) \\
  \forall x \cdot \exists y \cdot \neg hates(x,y) ... (7)
\end{aligned}
\end{equation}
$$

**Submission:** Submit the permalink in [src/main/java/de/buw/fm4se/smtsolving/Tasks.java (task_1)](src/main/java/de/buw/fm4se/smtsolving/Tasks.java)

## Task 2: Math Challenge
1. Find the puzzle on [puzzle.md](puzzle.md)
2. Use the following template link to encode the Puzzle: 

Note: use the same names as in the template to encode the puzzle.

Submission: Submit the permalink in [src/main/java/de/buw/fm4se/smtsolving/Tasks.java (task_2)](src/main/java/de/buw/fm4se/smtsolving/Tasks.java)

## Task 3: PC Configurations

Create a reusable encoding of the selection of PC components (see below table) and the calculated price of a PC. 

- The selection must satisfy the listed constraints.
- Each component may only be selected once, i.e., you cannot include 2xRTX 3060 Ti.

The encoding should be reusable in the following sense:
- A user states a purpose and budget and the encoding produces models that represent PC configurations matching the requirement, if possible.
- Different purposes add further constraints listed below.

Use the following template to start with. It already contains the encoding of the variables budget and purpose. Keep these names.

**Template:** [https://play.formal-methods.net/?check=SMT&p=ex-3-task-3](https://play.formal-methods.net/?check=SMT&p=ex-3-task-3)

**HINT (if-then-else):** You can define numbers based on conditions as shown in the following example where the food price increases by two if the user chose to add sauce.

Try it: [https://play.formal-methods.net/?check=SMT&p=nutty-zap-gnarly-ferret](https://play.formal-methods.net/?check=SMT&p=nutty-zap-gnarly-ferret)

```smt2
(declare-const foodPrice Int)
(declare-const serviceFee Int)
(declare-const withSauce Bool)
(assert (= 10 (+ foodPrice 
               (ite withSauce 2 0)
                serviceFee
    )))
(assert (<= foodPrice 5))
(check-sat)
(get-model)
```


| Component     |                              |                              |                                   |
|---------------|------------------------------|------------------------------|-----------------------------------|
| CPU           | Intel Core i3-12100F  (€113) | Intel Core i7-12700 (€360)   | AMD Ryzen 7 5700X  (€230)         |
| Mainboard     | Gigabyte H610M H Intel (€90) |                              | MSI B450-A PRO MAX AMD (€120)     |
| RAM           | 8GB 3200MHz DDR4  (€25)      | 32GB DDR4 3200Mhz (€99)      | 16GB DDR4 2666MHz  (€40)          |
| GPU           | RTX 3060 Ti 8GB LHR  (€699)  |                              | GTX 1650 OC 4GB (€230)            |
| Storage       | 1TB HDD  (€39)               | 2TB 2.5" SATA III SSD (€185) | 1TB NVMe M.2 2280 PCle SSD  (€90) |
| Optical Drive | Asus DVDRW SATA (€16)        |                              |                                   |
| Cooler        | Li ST120 Case Fan (€40)      |                              |                                   |


**Constraints:**
1.	Each computer needs basic components: CPU, Motherboard, RAM, Storage
2.	Intel CPU requires an Intel-based motherboard while Ryzen CPU requires an AMD-based motherboard.

**Purpose:**
1.	Office use requires optical drive
2.	Server use requires a cooler
3.	Gaming requires RTX GPU and a cooler
4.	Video editing requires an SSD

**Submission:** Submit the permalink in [src/main/java/de/buw/fm4se/smtsolving/Tasks.java (task_3)](src/main/java/de/buw/fm4se/smtsolving/Tasks.java)

