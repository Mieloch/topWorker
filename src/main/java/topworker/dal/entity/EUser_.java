package topworker.dal.entity;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(EUser.class)
public class EUser_ {
    public static volatile SingularAttribute<EUser, String> login;
    public static volatile SingularAttribute<EUser, Integer> id;
    public static volatile SetAttribute<EUser, EWorkPeriod> workPeriods;

}