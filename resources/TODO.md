# Punto della situazione
## Progetto
### DomainDSL
- VariableProvider
  - uno solo?
    - https://github.com/pikalab-unibo-students/ise-2122-project-brugnatti/commit/cd75495dd5569ae73a0c40718a105228d8cea8a8#r80965914
- Axiom
  - come gestire expression con unary e binary expression come sottotipi

### ProblemDSL
- da controllare tutto quello che viene istanziato perché ho provato a implementarlo io seguendo le tue indicazioni.
  - classi
    - ProblemDSL
    - GoalDSL
      - soprattutto l'utilizzo del Pair che non mi convince ma non sapevo come gestire
    - StatesDSL
- problemi da sistemare:
  - ProblemDSL
    - come gestire 
      - il domain (i link sono diversi)
        - https://github.com/pikalab-unibo-students/ise-2122-project-brugnatti/commit/b10b7869f240dd56938f19bf0ce67436e56ba816#r80851875
        - https://github.com/pikalab-unibo-students/ise-2122-project-brugnatti/commit/b10b7869f240dd56938f19bf0ce67436e56ba816#r80805612
      - il provider
        - https://github.com/pikalab-unibo-students/ise-2122-project-brugnatti/commit/b10b7869f240dd56938f19bf0ce67436e56ba816#r80772504

Rivedere catena di chaimate delle lamba

### Gradle
- il progetto si è spaccato quando ho fatto una pull da develop per recuperare la nuova versione degli axiom, per via del fatto che le librerie erano compilate con una versione nuova di kotlin, ma io quell'aggiornamento non lo vedo; ti allego gli screenshot. Ovviamente, per evitare di bloccarmi ho ripristinato il build.gradle.kts che avevo su questo branch prima della pull e ora rifunziona tutto, ma è una roba che è da sistemare.

### Relazione
- rispondere