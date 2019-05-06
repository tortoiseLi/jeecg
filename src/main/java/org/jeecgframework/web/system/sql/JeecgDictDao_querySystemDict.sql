select DISTINCT TYPECODE as TYPECODE,  TYPENAME  as TYPENAME 
from t_s_type t,t_s_type_group  g
where  g.code = :dicCode
and t.typegroupid =  g.id