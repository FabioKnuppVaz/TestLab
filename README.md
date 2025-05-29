# TestLab
Projeto de testes gerais

## Build da imagem (sem rodar container):
docker-compose -f docker-compose.build.yml build

## Subir a aplicação:
docker-compose -f docker-compose.yml up -d

## Comando direto com docker build, sem docker-compose:
docker build -t testlab-image -f Dockerfile .