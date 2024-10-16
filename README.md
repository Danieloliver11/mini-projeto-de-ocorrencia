**Projeto de ocorrencias Carbigdata**

Este projeto implementa uma API REST utilizando a linguagem de programação Java na versão 21, com o framework Spring Boot. Além disso, está integrado o Flyway Migrations para criar e popular as tabelas do banco de dados.

O projeto inclui um arquivo Docker Compose, que contém comandos para executar um servidor de arquivos e imagens de objetos MinIO (Storage), previamente configurado em um container. Também está prevista a criação do PostgreSQL, que será armazenado em um servidor de banco de dados, igualmente configurado em um container.


O primeiro passo é executar o comando no diretório onde está localizado o arquivo docker-compose.yml. Abra o terminal da sua máquina e execute o seguinte comando: "docker-compose up -d". Esse comando iniciará nossas aplicações em containers de forma local.

Em seguida, acesse o seu navegador e digite o seguinte endereço: http://localhost:9090/login. Utilize as credenciais padrão:

	•	Usuário: minioadmin
	•	Senha: minioadmin

Após realizar o login, vá até o menu “Administrador” e clique em “Buckets” na parte esquerda da tela. Na parte superior direita, clique no botão “Create Bucket”. Insira o nome do seu bucket: “ocorrencias-carbigdata” (é importante que o nome seja exatamente este, pois será utilizado na API do back-end). Por fim, clique em “Create Bucket” para criar o novo bucket, onde armazenaremos nossos arquivos.

Por fim, acesse http://localhost:8080/ocorrencia/swagger-ui/index.html#/ para visualizar a documentação de todas as nossas APIs. As APIs implementam tem verificação via TOKEN JWT, portanto, é necessário criar um CLIENTE no endpoint POST /Clientes antes de realizar o login e gerar o token. Esse token será utilizado para acessar os demais endpoints do sistema.

No banco de dados criado, há um único usuário administrador (ADM). Esse usuário tem permissão para visualizar qualquer ocorrência, mesmo que não seja de sua autoria, e é o único autorizado a finalizar ocorrências.

Credenciais do administrador:

	•	CPF: 503.989.630-10
	•	Senha: “root”
