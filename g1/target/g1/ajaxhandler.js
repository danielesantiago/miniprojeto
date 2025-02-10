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


function atualizarLances() {
    const ajax = initajax();

    if (ajax) {
        ajax.onreadystatechange = function () {
            if (ajax.readyState === 4) {
                if (ajax.status === 200) {

                    const data = JSON.parse(ajax.responseText);

                    const lancesSection = document.getElementById('lances');

                    lancesSection.innerHTML = '<h2>Lances Feitos</h2>';

                    if (data.length > 0) {
                        data.forEach(lance => {
                            const div = document.createElement('div');
                            div.classList.add('lance-item'); 

                            div.innerHTML = `
                                <strong>Usuário:</strong> ${lance.nome}<br>
                                <strong>Produto:</strong> ${lance.produto}<br>
                                <strong>Valor:</strong> R$ ${lance.valor_lance.toFixed(2)}<br><br>
                            `;

                            lancesSection.appendChild(div);
                        });
                    } else {
                        const p = document.createElement('p');
                        p.textContent = 'Nenhum lance disponível no momento.';
                        lancesSection.appendChild(p);
                    }
                    const formElements = document.getElementById("inserirLance").querySelectorAll("input, button");
                    formElements.forEach(elem => elem.disabled = true);

                    setTimeout(() => {
                        formElements.forEach(elem => elem.disabled = false);
                    }, 5000);
                } else {
                    console.error('Erro ao atualizar lances:', ajax.statusText);
                }
            }
        };

        ajax.open('GET', 'lances', true);
        ajax.send(null);
    }
}

setInterval(atualizarLances, 45000);
atualizarLances();