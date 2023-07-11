# Fordítás

`cd src`

`dir`

*látszik drukmakor mappa*

`javac drukmakor/*.java`

Jar fájl készítése:

`jar cfm druk.jar MANIFEST.MF drukmakor/*.class`

# Futtatás

`cd ..`

`java -jar src/druk.jar`

A programnak szüksége van képekre, és a palya.txt-re a futáshoz, ezeknek a current working directory-ban elérhetőnek kell lenniük (a fentebbi utasítások pontos végrehajtásának eredményeképp ezen fájlok megfelelő pozíciója biztosított kell legyen).

Használható parancssori argumentumok:
- `nogui`: csak parancssor
- `stdingui`: gui mellett még a parancssorból (stdin-ről) is fogad parancsokat
