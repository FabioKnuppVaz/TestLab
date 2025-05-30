# TestLab
Projeto de testes gerais

## Build da imagem (sem rodar container):
docker-compose -f docker-compose.build.yml build

## Subir a aplicação:
docker-compose -f docker-compose.yml up -d

## Comando direto com docker build, sem docker-compose:
docker build -t testlab-image -f Dockerfile .

## Rodar o sonar com Jacoco
mvn clean verify sonar:sonar ^  
-Dsonar.projectKey=TestLab ^  
-Dsonar.projectName='TestLab' ^  
-Dsonar.host.url=http://localhost:9000 ^  
-Dsonar.token=sqp_fe0f52347c3b2ac341490e806aa81066c279ebc3 test  