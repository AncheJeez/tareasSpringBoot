-- Script para insertar datos de prueba en la tabla empresa
TRUNCATE TABLE empresa RESTART IDENTITY CASCADE;

INSERT INTO empresa (nombre, nif, direccion_social, razon_social, capital_social, fecha_constitucion) VALUES
('Acme Corporation', 'A12345678', 'Calle Principal 123, Madrid', 'Acme Corp S.L.', 500000, '2015-03-15'),
('Tech Solutions S.L.', 'B87654321', 'Avenida Tecnologica 456, Barcelona', 'Tech Solutions S.L.', 250000, '2016-07-20'),
('Global Industries Inc', 'C11223344', 'Paseo Industrial 789, Valencia', 'Global Ind S.A.', 1000000, '2010-01-10'),
('Innovatech Enterprise', 'D55667788', 'Carrera Innovacion 321, Bilbao', 'Innovatech S.A.', 750000, '2013-11-25'),
('Digital Services Group', 'E99887766', 'Plaza Digital 654, Sevilla', 'Digital Services S.L.', 400000, '2018-05-08'),
('Prime Consulting Ltd', 'F44332211', 'Bulevar Consultoria 987, Malaga', 'Prime Consulting S.L.', 300000, '2017-09-12'),
('Summit Manufacturing', 'G12121212', 'Zona Industrial 111, Zaragoza', 'Summit Mfg S.A.', 2000000, '2008-04-03'),
('Nexus Technologies', 'H34343434', 'Parque Tecnologico 222, Alicante', 'Nexus Tech S.L.', 600000, '2019-02-14'),
('Elite Business Partners', 'I56565656', 'Calle de Negocios 333, Cordoba', 'Elite Partners S.L.', 450000, '2014-08-22'),
('Vertex Commerce Systems', 'J78787878', 'Avenida Comercial 444, Murcia', 'Vertex Commerce S.A.', 550000, '2012-06-30'),
('Aurora Development Corp', 'K90909090', 'Carrera Desarrollo 555, Palma', 'Aurora Dev S.L.', 350000, '2016-12-05'),
('Quantum Solutions Ltd', 'L11111111', 'Plaza Quantum 666, Las Palmas', 'Quantum Sol S.A.', 800000, '2011-10-18');

COMMIT;

SELECT * FROM empresa;