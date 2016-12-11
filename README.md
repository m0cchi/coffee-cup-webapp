
# build
```sh
git clone https://github.com/m0cchi/coffee-cup.git
cd coffee-cup/CoffeeCup
mkdir bin
find src/ -type f -print | grep java > srcfiles
javac -d bin @srcfiles
cd ../../
git clone https://github.com/m0cchi/coffee-cup-webapp.git
cd coffee-cup-webapp
mkdir bin
find src/ -type f -print | grep java > srcfiles
javac -cp ../../coffee-cup/CoffeeCup/bin/ -d bin @srcfiles
```

# run
```sh
java -cp bin:../coffee-cup/CoffeeCup/bin net.m0cchi.startup.Bootstrap
```
