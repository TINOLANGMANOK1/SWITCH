/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.at001_allawan_amper;

import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

/**
 *
 * @author Students Account
 */
public class AT001_ALLAWAN_AMPER {

  public static void main(String[] args) {

        Random random = new Random();
        Scanner scanner = new Scanner(System.in);

        int playerHp = 100;
        int monsterHp = 20; 

        int playerMinDmg = 5;
        int playerMaxDmg = 10;

        int monsterMinDmg = 5;
        int monsterMaxDmg = 10;

        boolean monsterStunned = false;

        Stack<Integer> monsterHpStack = new Stack<>();
        Stack<Integer> desperateGambitStack = new Stack<>();
        boolean desperateGambitActive = false;
        boolean desperateGambitNerf = false;
        boolean desperateGambitUsed = false;

        Stack<Integer> jinguBuffStack = new Stack<>();
        int jinguHitCounter = 0;

        int[] doomReflection = new int[2];
        doomReflection[0] = 0;
        doomReflection[1] = 0;

        int[] stunCooldown = new int[2];
        stunCooldown[0] = 0;
        stunCooldown[1] = 0;

        System.out.println("YOU ENCOUNTERED AN ENEMY!");

        while (true) {
            System.out.println("Player Hp: " + playerHp);
            System.out.println("Monster Hp: " + monsterHp);
            System.out.println();

            if (playerHp <= 0 && monsterHp <= 0) {
                System.out.println("It's a draw!");
                break;
            } else if (playerHp <= 0) {
                System.out.println("You lost! The monster wins!");
                break;
            } else if (monsterHp <= 0 && !desperateGambitActive && !desperateGambitNerf && !desperateGambitUsed) {
                System.out.println("Congratulations! You defeated the monster!");
                break;
            }

            // -------- PLAYER'S TURN --------
            System.out.println("PLAYER'S TURN!");
            System.out.println("1. Normal Attack");
            String stunStatus = (stunCooldown[0] == 1 ? "Cooldown: " + stunCooldown[1] : "Ready");
            System.out.println("2. Stun Attack (" + stunStatus + ")");
            System.out.println("3. Skip Turn");
            String doomStatus = (doomReflection[0] == 2) ? ("Cooldown: "+doomReflection[1]) 
                                : (doomReflection[0] == 1 ? ("Active: "+doomReflection[1]+" turn(s) left") 
                                : "Ready");
            System.out.println("4. Activate Doom Reflection (" + doomStatus + ")");
            System.out.print("Choose (or type exit): ");

            String input = scanner.next();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("You exited the game.");
                break;
            }

            if (input.equals("1")) {
                jinguHitCounter++;
                int playerDmg = playerMinDmg + random.nextInt(playerMaxDmg - playerMinDmg + 1);

                if (jinguBuffStack.isEmpty() && jinguHitCounter == 4) {
                    System.out.println("Passive Jingu Mastery is activated!");
                    System.out.println("Jingu Mastery activated! Next 4 normal attacks gain +4 damage and 80% lifesteal!");
                    for (int i = 0; i < 4; i++) {
                        jinguBuffStack.push(1);
                    }
                    jinguHitCounter = 0;
                }

                if (!jinguBuffStack.isEmpty()) {
                    playerDmg += 4;
                    int lifesteal = (int)Math.round(playerDmg * 0.80);
                    playerHp += lifesteal;
                    System.out.println("Jingu Mastery buff: +4 damage and +" + lifesteal + " HP (lifesteal)!");
                    jinguBuffStack.pop();
                }

                System.out.println("You dealt " + playerDmg + " damage to the monster.");
                monsterHpStack.push(monsterHp);
                monsterHp -= playerDmg;
                if (monsterHp < 0) monsterHp = 0;
                System.out.println("Monster remaining hp: " + monsterHp);
                System.out.println();

                if (monsterHp <= 0 && !desperateGambitActive && !desperateGambitNerf && !desperateGambitUsed) {
                    int chance = random.nextInt(1
                    ); 
                    if (chance == 0) {
                        System.out.println("Monster's passive activates: 'Desperate Gambit'!");
                        monsterHp = 50; 
                        System.out.println("The monster regains 50% HP: " + monsterHp);
                        System.out.println("The monster is empowered for 3 turns! ( +10% damage )");
                        desperateGambitActive = true;
                        desperateGambitStack.push(3); 
                        desperateGambitUsed = true;
                        monsterMinDmg = 5;
                        monsterMaxDmg = 15;
                    } else {
                        System.out.println("Congratulations! You defeated the monster!");
                        break;
                    }
                }
            } else if (input.equals("2")) {
                if (stunCooldown[0] == 1) {
                    System.out.println("Stun Attack is on cooldown! (" + stunCooldown[1] + " turn(s) left)");
                    System.out.println();
                } else {
                    jinguHitCounter = 0; 
                    int stunChance = random.nextInt(4); 
                    if (stunChance == 0) {
                        monsterStunned = true;
                        System.out.println("Stun successful! The monster is stunned and will skip its turn.");
                    } else {
                        System.out.println("Stun failed! The monster is not stunned.");
                    }
                    stunCooldown[0] = 1;
                    stunCooldown[1] = 4;
                    System.out.println();
                }
            } else if (input.equals("3")) {
                jinguHitCounter = 0;
                System.out.println("You skipped your turn!");
                System.out.println();
            } else if (input.equals("4")) {
                if (doomReflection[0] == 1) {
                    System.out.println("Doom Reflection is already active! (" + doomReflection[1] + " turn(s) left)");
                } else if (doomReflection[0] == 2) {
                    System.out.println("Doom Reflection is on cooldown! (" + doomReflection[1] + " turn(s) left)");
                } else {
                    doomReflection[0] = 1;
                    doomReflection[1] = 3; 
                    System.out.println("You activate DOOM REFLECTION! Next 3 attacks will be reflected.");
                }
                System.out.println();
            } else {
                System.out.println("Invalid input! Please enter 1, 2, 3, 4 or 'exit'.");
                System.out.println();
                continue;
            }

            if (stunCooldown[0] == 1) {
                stunCooldown[1]--;
                if (stunCooldown[1] <= 0) {
                    stunCooldown[0] = 0;
                    stunCooldown[1] = 0;
                    System.out.println("Stun Attack cooldown finished! Skill is ready to use again.");
                }
            }

            if (doomReflection[0] == 2) {
                doomReflection[1]--;
                if (doomReflection[1] <= 0) {
                    doomReflection[0] = 0;
                    doomReflection[1] = 0;
                    System.out.println("Doom Reflection cooldown finished! Skill is ready to use again.");
                }
            }

            // -------- MONSTER'S TURN --------
            if (monsterHp > 0) {
                if (monsterStunned) {
                    System.out.println("Monster is stunned and cannot attack this turn.");
                    System.out.println();
                    monsterStunned = false;
                } else {
                    int monsterDmg = monsterMinDmg + random.nextInt(monsterMaxDmg - monsterMinDmg + 1);

                    if (desperateGambitActive && !desperateGambitStack.isEmpty()) {
                        monsterDmg = monsterDmg + (monsterDmg * 10 / 100);
                        int turnsLeft = desperateGambitStack.pop() - 1;
                        if (turnsLeft > 0) {
                            desperateGambitStack.push(turnsLeft);
                        } else {
                            desperateGambitActive = false;
                            desperateGambitNerf = true;
                            System.out.println("Desperate Gambit's power fades. Monster now deals half damage and its max damage returns to normal!");
                            monsterMinDmg = 5;
                            monsterMaxDmg = 10;
                        }
                    } else if (desperateGambitNerf) {
                        monsterDmg = monsterDmg / 2;
                    }

                    // -- DOOM REFLECTION CHECK --
                    if (doomReflection[0] == 1 && doomReflection[1] > 0) {
                        int reflectRoll = random.nextInt(4); 
                        if (reflectRoll == 0) {
                            System.out.println("DOOM REFLECTION: Perfect reflect! Monster takes " + monsterDmg + " damage, you take 0!");
                            monsterHp -= monsterDmg;
                            if (monsterHp < 0) monsterHp = 0;
                        } else {
                            int reflected = monsterDmg / 2;
                            System.out.println("DOOM REFLECTION: You reflect " + reflected + " damage back! You take " + monsterDmg + " damage.");
                            monsterHp -= reflected;
                            playerHp -= monsterDmg;
                            if (playerHp < 0) playerHp = 0;
                            if (monsterHp < 0) monsterHp = 0;
                        }
                        doomReflection[1]--;
                        if (doomReflection[1] <= 0) {
                            doomReflection[0] = 2; 
                            doomReflection[1] = 6; 
                            System.out.println("Doom Reflection has ended! Cooldown started.");
                        } else {
                            System.out.println("Doom Reflection remains active for " + doomReflection[1] + " more turn(s).");
                        }
                        System.out.println();
                    } else {
                        System.out.println("MONSTER'S TURN!");
                        System.out.println("Monster attacks and deals " + monsterDmg + " damage to you.");
                        playerHp -= monsterDmg;
                        if (playerHp < 0) playerHp = 0;
                        System.out.println("Your remaining hp: " + playerHp);
                        System.out.println();
                    }

                    // --- REGENERATION PASSIVE ---
                    int regenChance = random.nextInt(4); 
                    if (!monsterHpStack.isEmpty() && regenChance == 0) {
                        int prevHp = monsterHpStack.peek();
                        System.out.println("Monster activates passive: 'Regeneration'!");
                        System.out.println("Regeneration: Monster restores its HP to " + prevHp);
                        monsterHp = prevHp;
                        System.out.println();
                    }
                }
            }

            if (monsterHp <= 0) {
                System.out.println("Congratulations! You defeated the monster!");
                break;
            }
            if (playerHp <= 0 && monsterHp <= 0) {
                System.out.println("It's a draw!");
                break;
            } else if (playerHp <= 0) {
                System.out.println("You lost! The monster wins!");
                break;
            }
        }
    }
}


