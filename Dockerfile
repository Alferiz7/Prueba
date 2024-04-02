FROM openjdk:17

WORKDIR /usrapp/bin

ENV PORT 6000
ENV MATH_SERVICES=http://ec2-34-203-229-114.compute-1.amazonaws.com:4568;http://ec2-34-229-252-65.compute-1.amazonaws.com:4568

COPY /target/classes /usrapp/bin/classes
COPY /target/dependency /usrapp/bin/dependency

CMD ["java","-cp","./classes:./dependency/*","edu.eci.arep.ServiceProxy"]