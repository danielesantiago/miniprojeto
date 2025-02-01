function initajax(){
    return new XMLHttpRequest();
}

function enviarLance(event){
    event.preventDefault();
    ajax = initajax();
    
    dados="nome="+document.getElementById("nome").value+"&";
    dados+="produto="+document.getElementById("produto").value+"&";
    dados+="lance="+document.getElementById("lance").value+"&";

    if(ajax){
        ajax.onreadystatechange=function(){
            if (ajax.readyState==4){
                if (ajax.status==200){
                //quando req volta certa
                    alert(ajax.responseText)
                    document.getElementById("inserirLance").reset();
                }
            else alert(ajax.responseText)
            }
        }
        
        url = "lanceservlet?"+dados;
        ajax.open('GET', url, true);
        ajax.send(null);

    }
}


function initajax() {
    return new XMLHttpRequest();
}

function atualizarLances() {
    const ajax = initajax();

    if (ajax) {
        ajax.onreadystatechange = function () {
            if (ajax.readyState === 4) {
                if (ajax.status === 200) {
                    // Converte a resposta JSON em um objeto JavaScript
                    const data = JSON.parse(ajax.responseText);

                    // Atualiza o conteúdo da seção de lances
                    const lancesSection = document.getElementById('lances');
                    
                    // Remove o texto padrão
                    lancesSection.innerHTML = '<h2>Lances Feitos</h2>';

                    // Itera sobre os lances e cria elementos de exibição
                    if (data.length > 0) {
                        data.forEach(lance => {
                            const div = document.createElement('div');
                            div.classList.add('lance-item'); // Estilize esta classe no CSS

                            div.innerHTML = `
                                <strong>Usuário:</strong> ${lance.nome}<br>
                                <strong>Produto:</strong> ${lance.produto}<br>
                                <strong>Valor:</strong> R$ ${lance.valor_lance.toFixed(2)}
                            `;

                            lancesSection.appendChild(div);
                        });
                    } else {
                        const p = document.createElement('p');
                        p.textContent = 'Nenhum lance disponível no momento.';
                        lancesSection.appendChild(p);
                    }
                } else {
                    console.error('Erro ao atualizar lances:', ajax.statusText);
                }
            }
        };

        // Configura e envia a requisição para o servlet "/lances"
        ajax.open('GET', 'lances', true);
        ajax.send(null);
    }
}

// Atualiza os lances a cada 10 segundos
setInterval(atualizarLances, 10000);

// Carrega os lances na abertura da página
atualizarLances();
