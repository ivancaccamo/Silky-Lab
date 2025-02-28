# <p align = "center"> Silky Lab </p>


**<p align = "center"> Progetto di Ingegneria del Software </p>**

<p align = "center"> Mattia Brozzoni - 1087194 </p>
<p align = "center"> Ivan Caccamo - 1085892 </p>
<p align = "center"> Simone Preda - 1086298 </p>

_<p align = "center"> Università degli studi di Bergamo </p>_

_<p align = "center"> aa. 2024-2025 </p>_

## Obiettivo
Il progetto Silky Lab nasce con l’obiettivo di creare un e-commerce dedicato all’abbigliamento, che si distingue per l’attenzione alla qualità dei prodotti e un’esperienza utente semplice e intuitiva. L’interfaccia utente rappresenta un aspetto centrale dell’esperienza di Silky Lab, garantendo un design chiaro, minimale e facilmente navigabile, in linea con la filosofia del brand.
I capi offerti rifletteranno gli stessi concetti: semplicità e qualità.

## Istruzioni per clonare il repository e avviare l'applicazione
### 1. Clonare il repository GitHub
Assicurati di avere Git installato sul tuo sistema. Apri un terminale e usa il seguente comando per clonare il repository:

```sh
 git clone https://github.com/ivancaccamo/Silky-Lab.git
```

Dopo il completamento del download, accedi alla cartella del progetto:

```sh
 cd Silky-Lab
```

### 2. Importare il progetto in Eclipse
1. Apri Eclipse.
2. Seleziona **File** > **Import**.
3. Scegli **Existing Maven Projects**.
4. Clicca su **Next**.
5. Naviga alla cartella del repository clonato.
6. Seleziona il progetto e clicca su **Finish**.

### 3. Compilare e avviare l'applicazione
  ```sh
  mvn clean install
  mvn exec:java
  ```
  Oppure:
  1. Trova la classe principale 'Application.java', contenente il metodo `public static void main(String[] args) {}`.
  2. Clicca con il tasto destro sulla classe e seleziona **Run As** > **Java Application**.

Ora l'applicazione dovrebbe avviarsi correttamente!

🚀 Buon lavoro con Silky Lab!

