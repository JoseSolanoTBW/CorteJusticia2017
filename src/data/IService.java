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

    /**
     *
     * @return
     */
    Object[] get();

    /**
     *
     */
    void insert();

    /**
     *
     * @param id
     */
    void delete(int id);

    /**
     *
     */
    void update();
}
