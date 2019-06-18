package com.simulation.common.base;

import java.io.Serializable;
import java.util.List;

public abstract interface BaseMybatisService<T, PK extends Serializable>
{
  public abstract T selectByPrimaryKey(PK paramPK);

  public abstract void deleteByPrimaryKey(PK paramPK);

  public abstract void insert(T paramT);

  public abstract void insertSelective(T paramT);

  public abstract void updateByPrimaryKeySelective(T paramT);

  public abstract void updateByPrimaryKey(T paramT);

  public abstract List<T> findAll();

  public abstract void deleteAll();
}
