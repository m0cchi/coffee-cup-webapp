FROM frolvlad/alpine-oraclejdk8

RUN apk add --update git && rm -rf /var/lib/apt/lists/*
RUN git clone https://github.com/m0cchi/coffee-cup.git \
    && cd coffee-cup/CoffeeCup \
    && mkdir bin \
    && find src/ -type f -print | grep java > srcfiles \
    && javac -d bin @srcfiles \
    && cd ../../ \
    && git clone https://github.com/m0cchi/coffee-cup-webapp.git \
    && cd coffee-cup-webapp \
    && mkdir bin \
    && find src/ -type f -print | grep java > srcfiles \
    && javac -cp ../../coffee-cup/CoffeeCup/bin/ -d bin @srcfiles

EXPOSE 18080

CMD cd /coffee-cup-webapp \
    && java -cp /coffee-cup-webapp/bin:/coffee-cup/CoffeeCup/bin net.m0cchi.startup.Bootstrap
