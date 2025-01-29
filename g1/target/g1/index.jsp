<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Portal de leilões</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <div id="container">
	<!-- Seção de Usuário -->
    <section id="usuario">
        <h2>Realizar Lance</h2><br>
        <form action="lanceservlet" method="post">
            <label for="nome">Nome de Usuário:</label>
            <input type="text" id="nome" name="nome" placeholder="Digite seu nome de usuário"><br>
            
            <label for="produto">ID do Produto:</label>
            <input type="text" id="produto" name="produto" placeholder="Digite o ID do produto"><br>
            
            <label for="lance">Valor do Lance:</label>
            <input type="number" step="0.01" id="lance" name="lance" placeholder="Digite o valor do seu lance"><br><br>
            
            <button type="submit">Fazer Lance</button>
        </form>
    </section>

    <!-- Seção de Lances -->
    <section id="lances">
        <h2>Lances Feitos</h2>
        <p>Aqui será mostrado os lances.</p>
    </section>
    </div>
</body>
</html>
