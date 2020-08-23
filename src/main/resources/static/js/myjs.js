validate=function(){
name=document.querySelector("input[name='name']").value
price=document.querySelector("input[name='price']").value
quantity=document.querySelector("input[name='quantity']").value
if(name.length === 0 || !name.trim())
    {
    window.alert("Name Should not be Empty");
    return false;
    }
else if(price.length === 0 || !price.trim())
         {
         window.alert("Price Should not be Empty");
         return false;
         }
else if(quantity.length === 0 || !quantity.trim())
         {
         window.alert("Quantity Should not be Empty");
         return false;
         }
return true;
}

validate2=function(){
name=document.querySelector("input[name='name']").value
price=document.querySelector("input[name='login']").value
quantity=document.querySelector("input[name='password']").value
if(name.length === 0 || !name.trim())
    {
    window.alert("Name Should not be Empty");
    return false;
    }
else if(price.length === 0 || !price.trim())
         {
         window.alert("Login Should not be Empty");
         return false;
         }
else if(quantity.length === 0 || !quantity.trim())
         {
         window.alert("Password Should not be Empty");
         return false;
         }
return true;
}