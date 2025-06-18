# TestLab
Projeto de testes gerais

## Build da imagem (sem rodar container):
docker-compose -f docker-compose.build.yml build

## Subir a aplicação:
docker-compose -f docker-compose.yml up -d

## Comando direto com docker build, sem docker-compose:
docker build -t testlab-image -f Dockerfile .

## Rodar o sonar com Jacoco
mvn verify sonar:sonar ^  
-Dsonar.projectKey=TestLab ^  
-Dsonar.projectName='TestLab' ^  
-Dsonar.host.url=http://localhost:9000 ^  
-Dsonar.token=sqp_354e4a1832556d8a238bbca77ce10f1c809f60ba  