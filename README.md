# Fordítás

`cd src`

`dir`

*látszik drukmakor mappa*

`javac drukmakor/*.java`

# Futtatás

`java drukmakor.Skeleton`

A futtatás során az inicializáció is tesz fel esetenként kérdéseket (az is függ az objektumok
belső állapotától), az inicializáló helyes lefutásához az isOccupied kérdésekre false-t
kell kapnia a programnak.

# Jar fájl készítése

Fordítjuk a javac-cal a kódot ugyanúgy mint fentebb, majd létrehozzuk a manifest fájlt:

`echo "Main-Class: drukmakor.Skeleton" > manifest.mf`

és ennek felhasználásával a jar fájlt:

`jar cfm skeleton.jar manifest.mf drukmakor/*.class`

# Jar fájl futtatása

`java -jar skeleton.jar`
