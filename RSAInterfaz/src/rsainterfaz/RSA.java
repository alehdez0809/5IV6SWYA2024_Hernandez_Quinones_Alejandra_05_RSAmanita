/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rsainterfaz;

/**
 *
 * @author edith_000
 */
import java.math.BigInteger;
import java.util.*;
import java.io.*;

public class RSA {
    
    //declarar variables
    int tamprimo;
    BigInteger p,q,n;
    BigInteger fi;
    BigInteger e,d;
    
    //constructor de la clase
    public RSA(int tamprimo){
        this.tamprimo = tamprimo;
    }
    //metodo para generar los numeros primos
    public void generarPrimos(){
        p = new BigInteger(tamprimo, 10, new Random());
        do{
            q = new BigInteger(tamprimo, 10, new Random());
        }while(q.compareTo(p) == 0);
    }
    //generar las claves
    //n = p*q
    //fi = (p-1)*(q-1)
    public void generarClaves(){
       n = p.multiply(q);
        
       fi = p.subtract(BigInteger.valueOf(1));
       fi = fi.multiply(q.subtract(BigInteger.valueOf(1)));
       
       //calcular e
       //e debe ser un numero entre 1<e<fi(n)
       
       do{
           e = new BigInteger(2*tamprimo, new Random());
       }while((e.compareTo(fi) != 1) || (e.gcd(fi).compareTo(BigInteger.valueOf(1)) != 0));
       
       //calculamos d = e 1 mod fi o sea el inverso multiplicativo de e 
       d = e.modInverse(fi);
       
    }
    
    //cifrar
    private BigInteger[] cifrar(String mensaje){
        int i;
        byte[] temp = new byte[1];
        byte[] digitos = mensaje.getBytes();
        
        BigInteger[] bigdigitos = new BigInteger[digitos.length];
        
        //tenemos que iterar ese mensaje para crear el nuevo BI
        for(i = 0; i < bigdigitos.length; i++){
            temp[0] = digitos[i];
            bigdigitos[i] = new BigInteger(temp);
        }
        
        //cifrarlo
        BigInteger[] cifrado = new BigInteger[bigdigitos.length];
        
        for(i = 0;i < bigdigitos.length; i++){
            cifrado[i] = bigdigitos[i].modPow(e, n);
        }
        return (cifrado);
    }
    
    public BigInteger[] getCifrar(String mensaje){
        return cifrar(mensaje);
    }
    
    private String descifrar(BigInteger[] cifrado){
        BigInteger[] descifrado = new BigInteger[cifrado.length];
        
        for(int i = 0; i < descifrado.length; i++){
            descifrado[i] = cifrado[i].modPow(d, n);
        }
        char[] charArray = new char[descifrado.length];
        
        for(int i=0; i<charArray.length; i++){
            charArray[i] = (char)(descifrado[i].intValue());
        }
        return(new String(charArray));
    }
    public String getDescifrar(BigInteger[] cifrado){
        return descifrar(cifrado);
    }
}
