FROM openjdk:17

WORKDIR /usrapp/bin

ENV PORT 6000
ENV MATH_SERVICES=http://localhost:4567;http://localhost:4568

COPY /target/classes /usrapp/bin/classes
COPY /target/dependency /usrapp/bin/dependency

CMD ["java","-cp","./classes:./dependency/*","edu.eci.arep.ServiceProxy"]