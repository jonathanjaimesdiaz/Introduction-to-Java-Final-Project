/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.blackjackcasino;

/**Name: Jonathan Jaimes-Diaz
 *Date: 5//2026
 *Assignment: Final project 
 *Description: 
 */
public class Player {
    private String playerName;
    private double money;
    private double currentBet;
    
    public Player() {
        playerName = "Guest";
        money = 0.0;
        currentBet = 0.0;
    }
    
    public Player(String playerName, double money) {
        this.playerName = playerName;
        this.money = money;
        currentBet = 0.0;
    }
    
    public String getPlayerName() {
        return playerName;
    }
    public double getMoney() {
        return money;
    }
    public double getCurrentBet() {
        return currentBet;
    }
    
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
    public void setMonet(double money) {
        this.money = money; 
    }
    public void setCurrentBet(double currentBet) {
        this.currentBet = currentBet;
    }
    
    public void placeBet(double betAmount) {
        currentBet = betAmount;
    }
    public void winBet() {
        money = money + currentBet;
    }
    public void loseBet() {
        money = money - currentBet;
    }
    public void pushBet() {
        //Push no moneyyyyyyy
    }
    
    
}
