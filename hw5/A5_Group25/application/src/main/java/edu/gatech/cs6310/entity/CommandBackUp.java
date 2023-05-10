package edu.gatech.cs6310.entity;/*
 *@author: lesliezhao
 *@version: 0.1
 */
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "commandbackup")
public class CommandBackUp {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "commandID")
    private long commandID;

    @Column(name = "commandTime")
    private LocalDateTime commandTime;

    @Column(name = "commandContent")
    private String commandContent;

    public CommandBackUp(){
    }

    public CommandBackUp(LocalDateTime commandTime,String commandContent){
        this.commandTime=commandTime;
        this.commandContent=commandContent;
    }

    public LocalDateTime getCommandTime() {
        return commandTime;
    }
}
