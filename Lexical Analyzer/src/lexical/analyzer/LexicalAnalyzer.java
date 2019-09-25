/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lexical.analyzer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
public class LexicalAnalyzer {

    static int charClass;
    static char[] lexeme = new char[100];
    static char nextChar;
    static int lexLen, token, nextToken;
    
    // character classes
    static final int LETTER = 0, DIGIT = 1, UNKNOWN = 99;
    
    // token codes
    static final int INT_LIT = 10, IDENT = 11, ASSIGN_OP = 20, ADD_OP = 21, SUB_OP = 22,
            MULT_OP = 23, DIV_OP = 24, LEFT_PAR = 25, RIGHT_PAR = 26, EOF = -1;
    
    static BufferedReader reader;
    static{
        try{
            reader = new BufferedReader(new FileReader("C:\\Users\\dcsmo\\Desktop\\lex analyzer.txt"));
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    
    public static void main(String[] args) throws IOException {
        
            getChar();
            do{
                lex();
            }while (nextToken != EOF);
        
    }
    
    static void addChar(){
        if(lexLen <= 98){
            lexeme[lexLen++] =  nextChar;
            lexeme[lexLen] = 0;
        }
        else
            System.out.printf("Error - lexeme is too long \n");
    }
    
    static void getChar() throws IOException {
        int c = 0;
        if((c =  reader.read()) != -1){
            nextChar = (char) c;
            if(Character.isLetter(nextChar))
                charClass = LETTER;
            else if(Character.isDigit(nextChar))
                charClass = DIGIT;
                else charClass = UNKNOWN;
        }
        else
            charClass = EOF;
    }
    static void getNonBlank() throws IOException {
        while (Character.isSpaceChar(nextChar)){
            getChar();
        }
    }
    static int lookup(char ch){
        switch (ch){
            case '(':
                addChar();
                nextToken = LEFT_PAR;
                break;
            case ')':      
                addChar();      
                nextToken = RIGHT_PAR;      
                break;
            case  '+':      
                addChar();      
                nextToken = ADD_OP;      
                break;
            case  '-':      
                addChar();      
                nextToken = SUB_OP;      
                break; 
            case  '*':      
                addChar();      
                nextToken = MULT_OP;      
                break;
            case  '/':      
                addChar();      
                nextToken = DIV_OP;      
                break;
            default:      
                addChar();      
                nextToken = EOF;      
                break;  
        } 
        return nextToken;                
    }
    static int lex()throws IOException {
        lexLen = 0;
        getNonBlank();
        switch (charClass){
            case  LETTER:      
                addChar();      
                getChar();      
                while  (charClass == LETTER || charClass == DIGIT) {        
                    addChar();        
                    getChar();      
                }    
                nextToken = IDENT;    
                break;
/* Parse integer literals */    
            case  DIGIT:      
                addChar();      
                getChar();      
                while  (charClass == DIGIT) {        
                    addChar();        
                    getChar();      
                }      
                nextToken = INT_LIT;      
                break;
/* Parentheses and operators */    
            case  UNKNOWN:      
                lookup(nextChar);      
                getChar();      
                break;
/* EOF */    
            case  EOF:      
                nextToken = EOF;      
                lexeme[0] = 'E';      
                lexeme[1] = 'O';      
                lexeme[2] = 'F';      
                lexeme[3] = 0;       
                break;  
        } /* End of switch */  
        
        System.out.printf("Next token is: %d, Next lexeme is: ", nextToken);
        for( int i = 0; i < lexLen; i++){
            System.out.print(lexeme[i]);
        }
        System.out.println();
        return  nextToken;
    }
}
