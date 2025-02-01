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