select DISTINCT TYPECODE as TYPECODE,  TYPENAME  as TYPENAME 
from t_s_type t,t_s_type_group  g
where  g.typegroupcode = :dicCode 
and t.typegroupid =  g.id