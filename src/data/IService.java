/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

/**
 *
 * @author mean
 */
public interface IService {
    Object[] get();
    void insert();
    void delete(int id);
    void update();
}