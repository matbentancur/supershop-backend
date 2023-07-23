package grupo1.supershop.instalar.cargar;

import grupo1.supershop.beans.Categoria;
import grupo1.supershop.beans.Producto;
import grupo1.supershop.beans.Usuario;
import grupo1.supershop.fabricas.FabricaSesion;
import grupo1.supershop.interfaces.controladores.IControladorCarrito;
import grupo1.supershop.interfaces.controladores.IControladorCompra;
import grupo1.supershop.interfaces.controladores.IControladorSesion;
import grupo1.supershop.persistencia.manejadores.ManejadorCategoria;
import grupo1.supershop.persistencia.manejadores.ManejadorProducto;
import grupo1.supershop.servicios.ServicioActividad;
import grupo1.supershop.servicios.ServicioSesion;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

public class CargarProducto {
    private static final String EMAIL_ADMIN = "administrador@supershop.uy";
    private static final String PASSW_ADMIN = "administrador@supershop.uy";

    private final IControladorSesion icSesion;

    public CargarProducto() {
        icSesion = FabricaSesion.getIControladorSesion();
    }

    public void cargarProductos() throws Exception {
        String secreto = iniciarSesion(EMAIL_ADMIN, PASSW_ADMIN).obtenerSecreto(EMAIL_ADMIN, PASSW_ADMIN);
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        String[][] datos = {
                {"COCA-COLA 3L", "3000", "220", "100000000", "COCA-COLA 3L", "1", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687898675/images_f1pjm0.jpg", ""},
                {"COCA-COLA 1.5L", "1500", "120", "100000001", "COCA-COLA 1.5L", "1", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687898871/images_rv5id0.jpg", ""},
                {"COCA-COLA Light 1.5L", "1500", "120", "100000002", "COCA-COLA Light 1.5L", "1", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687899000/images_wlvh7t.jpg", ""},
                {"SPRITE 2.25L", "2250", "160", "100000003", "SPRITE 2.25L", "1", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687899075/SPRITE-COMUN_ppictk.jpg", ""},
                {"SPRITE sin Azucar 1.5L", "1500", "130", "100000004", "SPRITE sin Azucar 1.5L", "1", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687899089/P125669-2.jpg_wkthog.jpg", ""},
                {"PASOS DE LOS TOROS Pomelo 1.5L", "1500", "110", "100000005", "PASOS DE LOS TOROS Pomelo 1.5L", "1", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687899098/refrescos-paso-de-los-toros-pomelo-1-5-litros-600x600_tuuwbj.png", ""},
                {"SCHWEPPES Pomelo 1.5L", "1500", "115", "100000006", "SCHWEPPES Pomelo 1.5L", "1", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687899218/schweppes-pomelo-1-5l-schweppes-pomelo-1-5l_mlzm6c.jpg", ""},
                {"Fernet BRANCA 750ml", "750", "440", "100000007", "Fernet", "1", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687899231/fernet-750ml_1000x1000_dwsnxe.png", ""},
                {"Cerveza CORONA 355ml", "355", "90", "100000008", "Cerveza CORONA 355ml", "1", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687899244/D_NQ_NP_926777-MLA54838216320_042023-O_sdhuzs.jpg", ""},
                {"Cerveza PILSEN 970ml", "970", "140", "100000009", "Cerveza PILSEN 970ml", "1", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687899332/pilsenlitro-modified_1000x1000_ivt5mw.png", ""},
                {"Cerveza ZILLERTAL 970ml", "970", "160", "100000010", "Cerveza ZILLERTAL 970ml", "1", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687899339/P082411-1_md167z.jpg", ""},
                {"Agua VITALE 2.5L", "2500", "80", "100000011", "Agua VITALE 2.5L", "1", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687899350/AguaVital1_4x_2517a11b-a6d7-4101-892a-ed13d7306ff5_1200x1200_lya7tv.png", ""},
                {"Agua SALUS 2.25L", "2250", "70", "100000012", "Agua SALUS 2.25L", "1", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687899363/3-3_vharod.jpg", ""},
                {"Agua SALUS Con Gas 2.25L", "2250", "75", "100000013", "Agua Con Gas 2.25L", "1", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687899500/Saluscongas2.25_8a3aec27-6e38-449c-b044-1968b29f2833_1000x1000_hnqiuq.png", ""},
                {"Jugo de Naranja CITRIC 1.5L", "1500", "200", "100000014", "Jugo de Naranja CITRIC 1.5L", "1", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687899513/7798085681483_02_ewebzw.jpg", ""},
                {"Jugo de Naranja ADES 1L", "1000", "93", "100000015", "Jugo de Naranja ADES 1L", "1", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687899524/ades_naranja__16976.1680264828_u9a3st.jpg", ""},
                {"Jugo de Naranja DAIRYCO 3L", "3000", "382", "100000016", "Jugo de Naranja DAIRYCO 3L", "1", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687899533/https_3A_2F_2Ftatauy.vtexassets.com_2Farquivos_2Fids_2F193619_2FJugo-Naranja-Dairyco-Bidon-3Lt-1-902_eqkrfi.jpg", ""},
                {"Jugo CONAPROLE Sabor Manzana 1L", "1000", "101", "100000017", "Jugo CONAPROLE Sabor Manzana 1L", "1", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687899545/P294852-2_i187sw.jpg", ""},
                {"Vino SANTA TERESA 1L", "1000", "130", "100000018", "Vino SANTA TERESA 1L", "1", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687899557/vino-santa-teresa-1l-tinto_nbhnuy.jpg", ""},
                {"Vino FAISAN 1L", "1000", "130", "100000019", "Vino FAISAN 1L", "1", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687899565/vino-faisan-1l-tinto-tannat-cabernet_cwz2qa.jpg", ""},
                {"Vino DE LA CAROLINA 1L", "1000", "120", "100000020", "Vino DE LA CAROLINA 1L", "1", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687899575/7730983181603-800x800_lxbcr3.jpg", ""},
                {"Whisky JOHNNIE WALKER Red Label 1L", "1000", "1050", "100000021", "Whisky JOHNNIE WALKER Red Label 1L", "1", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687899872/whisky-johnnie-walker-red-label-1l-whisky-johnnie-walker-red-label-1l_dlfk36.jpg", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687899883/whisky-johnnie-walker-red-label-1-litro-600x600_dtkzaj.png", ""},
                {"Whisky JOHNNIE WALKER Blue Label 750ml", "750", "16900", "100000022", "Whisky JOHNNIE WALKER Blue Label 750ml", "1", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687899895/J.Walker-blue-clasico_llj8kj.jpg", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687899916/whisky-johnnie-walker-blue-label-750-ml-001_wfgy5p.jpg", ""},
                {"Whisky CHIVAS REGAL 1L", "1000", "2100", "100000023", "Whisky CHIVAS REGAL 1L", "1", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687900004/Whisky-Escoces-CHIVAS-REGAL-12-A_C3_B1os-1-L-1_xykwtl.jpg", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687900014/whisky-chivas-regal-1-litro-600x600_ceqd4d.png", ""},
                {"Ron BACARDI Oro 750cc", "750", "800", "100000024", "Ron BACARDI Oro 750cc", "1", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687900027/94b411a0b8f7ce336ade76305b577b8ece747bb01cd7df0af55c04a0a03d342e_yldh5g.jpg", ""},
                {"Papas Fritas", "1000", "260", "100000025", "Papas Fritas", "4", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687900035/234045_f7q0bf.jpg", ""},
                {"Hamburguesa SCHNECK", "660", "520", "100000026", "Hamburguesa SCHNECK", "4", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687900119/hamburguesa-schneck-premium-xl-x-6-unidades_fv19jf.jpg", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687900128/hamburguesa-schneck-premium-xl-x-2-unidades_u1onrj.jpg", ""},
                {"Hamburguesa SARUBBI", "1014", "352", "100000027", "Hamburguesa SARUBBI", "4", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687900136/234239_mn2pa9.jpg", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687900146/hamburguesas_2_yckvf4.png", ""},
                {"Hamburguesa HAMBY", "668", "400", "100000028", "Hamburguesa HAMBY", "4", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687900177/P032354-2_ylsyl0.jpg", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687900185/hamburguesa-hamby-x-2-unid-hamburguesa-hamby-x-2-unid_cvj056.jpg", ""},
                {"Hamburguesa CAMPOSUR", "900", "410", "100000029", "Hamburguesa CAMPOSUR", "4", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687900195/hamburguesas-camposur-parrilleras-x12_e4p7om.jpg", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687900202/paquete-de-hamburguesa-camposur_kr24fm.jpg", ""},
                {"Helado CONAPROLE 1L", "1000", "270", "100000030", "Helado CONAPROLE 1L", "4", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687916754/261482-1550621079_cb59if.jpg", ""},
                {"Helado CRUFI 5L", "5000", "740", "100000031", "Helado CRUFI 5L", "4", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687916765/helado-crufi-frutilla-crema-y-chocolate-5-lts_azpg30.jpg", ""},
                {"Nuggets de Pollo Sadia", "3000", "815", "100000032", "Nuggets de Pollo Sadia", "4", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687916774/nuggets-de-pollo-crocantes-sadia-3kg_ugpzgg.jpg", ""},
                {"Nuggets de Pollo CRUFI", "900", "380", "100000097", "Nuggets de Pollo CRUFI", "4", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687916782/P515601-2_xtjsgb.jpg", ""},
                {"Ravioles de verdura LOS ABUELOS", "1000", "360", "100000033", "Ravioles de verdura LOS ABUELOS", "4", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687916790/ravioles-de-verdura-los-abuelos-1kg_patftk.jpg", ""},
                {"Ravioles de Jamon y Queso LOS ABUELOS", "1000", "380", "100000034", "Ravioles de Jamon y Queso LOS ABUELOS", "4", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687916797/ravioles-de-muzzarella-y-jamon-los-abuelos-1kg_l6q223.jpg", ""},
                {"Ravioles de verdura LA ESPECIALISTA", "500", "270", "100000035", "Ravioles de verdura LA ESPECIALISTA", "4", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687916807/P077873-1_px9htb.jpg", ""},
                {"Ravioles de 4 Quesos LA ESPECIALISTA", "500", "270", "100000036", "Ravioles de 4 Quesos LA ESPECIALISTA", "4", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687916815/P079401-1_t7nlcw.jpg", ""},
                {"Milanesas de Pollo AVICOLA DEL OESTE", "2000", "699", "100000037", "Milanesas de Pollo AVICOLA DEL OESTE", "4", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687916823/P073889-1_qgrqkt.jpg", ""},
                {"Milanesas de carne SCHNECK", "960", "570", "100000038", "Milanesas de carne SCHNECK", "4", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687916832/milanguesas-schneck-x-6-unidades-milanguesas-schneck-x-6-unidades_k4lv4j.jpg", ""},
                {"Milanesas de tofu NATUREZAS", "400", "310", "100000039", "Milanesas de tofu NATUREZAS", "4", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687916839/milanesas-de-tofu-naturezas-x4_am9gax.jpg", ""},
                {"Croquetas Jamon y Queso", "300", "110", "100000040", "Croquetas Jamon y Queso", "4", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687916849/MINI-CROQUETAS-DE-JYQ_f86f7m.jpg", ""},
                {"Silla de escritorio Cougar", "11000", "12600", "100000041", "Silla de escritorio Cougar Outrider gamer ergonómica outrider con tapizado de cuero sintético", "6", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687917045/D_NQ_NP_2X_730891-MLA50889341508_072022-F_wh69gl.webp", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687917052/D_NQ_NP_2X_693795-MLA50889343470_072022-F_szjd1n.webp", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687917059/D_NQ_NP_2X_851018-MLA50889343490_072022-F_q3npze.webp", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687917070/D_NQ_NP_2X_876402-MLA50889342548_072022-F_gngpxg.webp", ""},
                {"Silla de escritorio Lumax Rom", "8000", "4600", "100000098", "Silla de escritorio Lumax Rom gamer ergonómica negra y dorada con tapizado de piel sintética", "6", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687917149/D_NQ_NP_2X_996057-MLA51146843754_082022-F_oksckz.webp", ""},
                {"Escritorio Welaman Victoria", "4000", "6500", "100000042", "Escritorio Welaman Victoria mdf de 1.2m x 0.76m x 0.45m demolición", "6", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687917156/D_NQ_NP_2X_754595-MLA52224246112_102022-F_r0wsxa.webp", ""},
                {"Escritorio Appunto Self", "6000", "3000", "100000099", "Escritorio Appunto Self mdp de 135cm x 75cm x 60cm castaño y blanco", "6", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687917163/D_NQ_NP_2X_902507-MLA50330282571_062022-F_mvpisl.webp", ""},
                {"Sillon LUARES", "25000", "32000", "100000043", "SILLÓN - 2 CUERPOS TELA GRIS LUARES", "6", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687917169/sillon-2-cuerpos-tela-gris-luares_opsnrc.webp", ""},
                {"Sillon POLLOCK NAVY", "28000", "26000", "100000100", "SILLÓN - 2 CUERPOS TELA AZUL POLLOCK NAVY", "6", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687917251/sillon-2-cuerpos-tela-azul-pollock-navy_dl8xb0.webp", ""},
                {"Sillon LUXOR VISON", "26000", "22000", "100000101", "SILLÓN - 2 CUERPOS TELA MARRON LUXOR VISON", "6", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687917349/sillon-2-cuerpos-tela-marron-luxor-vison_de79kf.webp", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687917373/sillon-2-cuerpos-tela-marron-luxor-vison_dzgnle.webp", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687917383/sillon-2-cuerpos-tela-marron-luxor-vison_ou01t3.webp", ""},
                {"Mesa de luz AGATA", "8000", "2990", "100000044", "MESA DE LUZ - PINO MARRON AGATA", "6", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687917768/mesa-de-luz-pino-marron-agata_bq6anh.webp", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687917774/mesa-de-luz-pino-marron-agata_rft1ag.webp", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687917785/mesa-de-luz-pino-marron-agata_aiisvv.webp", ""},
                {"Mesa de luz MONTAUK", "9000", "7600", "100000102", "MESA DE LUZ - 1 CUERPO MADERA NATURAL-BEIGE MONTAUK", "6", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687917796/mesa-de-luz-1-cuerpo-madera-natural-beige-montauk_uayno2.webp", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687917806/mesa-de-luz-1-cuerpo-madera-natural-beige-montauk_st4oo8.webp", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687917816/mesa-de-luz-1-cuerpo-madera-natural-beige-montauk_x47frs.webp", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687917824/mesa-de-luz-1-cuerpo-madera-natural-beige-montauk_d1sudm.webp", ""},
                {"Mesa de luz HAVANA", "8000", "1800", "100000103", "MESA DE LUZ - 2 CAJONES MDP MARRON HAVANA", "6", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687917964/mesa-de-luz-2-cajones-mdp-marron-havana_ybrjml.webp", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687917970/mesa-de-luz-2-cajones-mdp-marron-havana_xtb0gi.webp", ""},
                {"Estanteria BONNIE", "4000", "11990", "100000045", "ESTANTERIA - MADERA NATURAL-BEIGE BONNIE", "6", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687917980/estanteria-madera-natural-beige-bonnie_dazd5r.webp", ""},
                {"Estanteria RACK", "6000", "13000", "100000104", "ESTANTERIA RACK - MADERA NATURAL-BEIGE MONTANA NATURAL", "6", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687917987/estanteria-rack-madera-natural-beige-montana-natural_xolgcn.webp", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687917996/estanteria-rack-madera-natural-beige-montana-natural_wcxqgf.webp", ""},
                {"Juego de comedor RUBBERWOOD", "45000", "78000", "100000046", "JUEGO DE COMEDOR - RUBBERWOOD MARRON KIMMO OVAL", "6", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687918007/juego-de-comedor-rubberwood-marron-kimmo-oval_wn51t3.webp", ""},
                {"Juego de comedor madera natural", "15000", "13000", "100000107", "JUEGO DE COMEDOR - MADERA NATURAL-BEIGE TEQUILA", "6", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687918013/juego-de-comedor-madera-natural-beige-tequila_d2gwwe.webp", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687918023/juego-de-comedor-madera-natural-beige-tequila_k8yolf.webp", ""},
                {"Juego de comedor", "18000", "21000", "100000105", "JUEGO DE COMEDOR - MADERA NATURAL-BEIGE BLOOMY", "6", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687918270/juego-de-comedor-madera-natural-beige-bloomy_bw30od.webp", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687918279/juego-de-comedor-madera-natural-beige-bloomy_daiy3d.webp", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687918288/juego-de-comedor-madera-natural-beige-bloomy_pfprbx.webp", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687918295/juego-de-comedor-madera-natural-beige-bloomy_mg74qd.webp", ""},
                {"Cama 1 plaza", "12000", "13000", "100000047", "ROCKY I - 1 PLAZA MDF-Y-MADERA BLANCO CAMA MARINERA", "6", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687918375/rocky-i-1-plaza-mdf-y-madera-blanco-cama-marinera_mkq6d6.webp", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687918383/rocky-i-1-plaza-mdf-y-madera-blanco-cama-marinera_jziyq8.webp", ""},
                {"Sommier", "14000", "32000", "100000048", "SOMMIER - 2 PLAZAS RESORTES POCKET DENSIDAD CRYSTAL", "6", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687918391/sommier-2-plazas-resortes-pocket-densidad-crystal_lwnibd.webp", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687918399/sommier-2-plazas-resortes-pocket-densidad-crystal_sayedm.webp", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687918407/sommier-2-plazas-resortes-pocket-densidad-crystal_lvvkvd.webp", ""},
                {"Monitor Samsung M5", "3500", "25000", "100000049", "Monitor Samsung M5", "3", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687918425/1-20_rwx7bj.jpg", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687918431/2-13_pgkhgo.jpg", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687918438/7_lrbfgo.jpg", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687918446/3-9_nn0hjn.jpg", ""},
                {"Monitor LG", "3000", "33000", "100000050", "Monitor LG 27″ UHD 4K IPS LED", "3", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687918828/1-65-5_bjsx01.jpg", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687918835/4-68-6_mjweyv.jpg", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687918841/5-55-2_z7t9xo.jpg", ""},
                {"Monitor Gaming ACER", "4000", "52500", "100000051", "Monitor Gaming ACER PREDATOR XB1 27″ IPS 144 Hz G-SYNC", "3", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687918637/1-25-10_vrinl6.jpg", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687918599/2-20-11-768x768_q80igi.jpg", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687918455/5-14-10-768x768_wjw8it.jpg", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687918460/6-12-10-768x768_rhqcfg.jpg", ""},
                {"Teclado LOGITECH K120", "300", "550", "100000052", "Teclado LOGITECH K120", "3", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687918920/1-12_flnf9u.png", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687918926/3-3_mwl7zi.png", ""},
                {"Teclado PHILIPS", "380", "380", "100000053", "Teclado PHILIPS SPK6254 Resistente Al Agua USB 2.0", "3", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687918933/1-56_zsvyte.jpg", ""},
                {"Mouse Inalámbrico Logitech M170", "300", "760", "100000054", "Mouse Inalámbrico Logitech M170", "3", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687918939/MOUSE-LOGITECH-M170-BLACK_jizhrc.jpg", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687918944/MOUSE-LOGITECH-M170-BLUE_yxbnsn.jpg", ""},
                {"Auriculares Logitech G233 Gaming Prodigy", "150", "5200", "100000055", "Auriculares Logitech G233 Gaming Prodigy", "3", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687918950/AURICULARES-LOGITECH-G233_anwucb.jpg", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687918957/AURICULARES-LOGITECH-G233-3_gqfhtr.jpg", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687918966/AURICULARES-LOGITECH-G233-2_pxijml.jpg", ""},
                {"Auricular LOGITECH G332 Gaming Con Micrófono", "150", "4300", "100000106", "Auricular LOGITECH G332 Gaming Con Micrófono", "3", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687918978/1-16-8_qnmd3l.jpg", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687918987/2-7-8_dydp1o.jpg", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687918993/3-7-8_o3hvah.jpg", ""},
                {"Auriculares JBL LIVE FREE NC+ TWS", "60", "3600", "100000056", "Auriculares JBL LIVE FREE NC+ TWS", "3", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687918999/JBL-1_fuoiz3.jpg", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687919004/JBL-2_xd26yu.jpg", ""},
                {"Auriculares APPLE AiPods MAX 2020", "30", "48600", "100000057", "Auriculares APPLE AiPods MAX 2020", "3", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687919012/5.0_ovvhny.jpg", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687919023/5.1_x1xsm4.jpg", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687919029/1-17_hz4vdq.jpg", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687919035/2-18-768x768_icxbca.jpg", ""},
                {"Smart Tv Xiaomi 43", "3000", "25000", "100000058", "Smart Tv Xiaomi Mi TV P1 43", "3", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687919053/MiTVP143-01_t1thfb.jpg", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687919061/MiTVP143-02_nxc6o0.jpg", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687919065/MiTVP143-04_nnjsdr.jpg", ""},
                {"Smart Tv UHD 4K SAMSUNG", "2500", "56000", "100000059", "SMART TV Frame SAMSUNG 55” UHD 4K", "3", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687919320/1-107_cms8dz.jpg", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687919326/3-58-768x768_zy63fg.jpg", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687919332/4-27-768x768_sajkg2.jpg", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687919339/2-90-768x768_gnyahw.jpg", ""},
                {"Notebook DELL", "3200", "29990", "100000062", "Notebook Dell Inspiron 3515 Ryzen 5 3450U 256GB 8GB 15.6\"", "3", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687919351/notebook-dell-inspiron-3515-ryzen-5-3450u-256gb-8gb-15-6-notebook-dell-inspiron-3515-ryzen-5-3450u-256gb-8gb-15-6_cuv7d2.webp", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687919361/notebook-dell-inspiron-3515-ryzen-5-3450u-256gb-8gb-15-6-notebook-dell-inspiron-3515-ryzen-5-3450u-256gb-8gb-15-6_xz1lhl.webp", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687919371/notebook-dell-inspiron-3515-ryzen-5-3450u-256gb-8gb-15-6-notebook-dell-inspiron-3515-ryzen-5-3450u-256gb-8gb-15-6_umxmnj.webp", ""},
                {"Notebook Asus", "3500", "32000", "100000063", "Notebook ASUS Laptop X515EA-BR3240W i5-1135G 512GB 8GB 15.6\"", "3", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687919382/notebook-asus-laptop-x515ea-br3240w-i5-1135g-512gb-8gb-15-6-notebook-asus-laptop-x515ea-br3240w-i5-1135g-512gb-8gb-15-6_gkd31h.webp", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687919391/notebook-asus-laptop-x515ea-br3240w-i5-1135g-512gb-8gb-15-6-notebook-asus-laptop-x515ea-br3240w-i5-1135g-512gb-8gb-15-6_mzyjre.webp", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687919400/notebook-asus-laptop-x515ea-br3240w-i5-1135g-512gb-8gb-15-6-notebook-asus-laptop-x515ea-br3240w-i5-1135g-512gb-8gb-15-6_o3jubp.webp", ""},
                {"Notebook Gamer MSI", "3000", "71000", "100000064", "Notebook Gamer MSI Sword 15 i7-11800H 512GB 8GB RTX 3050Ti", "3", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687919411/notebook-gamer-msi-sword-15-i7-11800h-512gb-8gb-rtx-3050ti-notebook-gamer-msi-sword-15-i7-11800h-512gb-8gb-rtx-3050ti_qzjkhx.webp", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687919417/notebook-gamer-msi-sword-15-i7-11800h-512gb-8gb-rtx-3050ti-notebook-gamer-msi-sword-15-i7-11800h-512gb-8gb-rtx-3050ti_fmzhpm.webp", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687919425/notebook-gamer-msi-sword-15-i7-11800h-512gb-8gb-rtx-3050ti-notebook-gamer-msi-sword-15-i7-11800h-512gb-8gb-rtx-3050ti_oc5xas.webp", ""},
                {"Cable HDMI", "5", "300", "100000065", "Cable HDMI MM 1.8 metros", "3", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687919436/cable-hdmi-mm-1-8-metros-cable-hdmi-mm-1-8-metros_cg4nj2.webp", ""},
                {"Consola Xbox Series X 1TB SSD", "4200", "39000", "100000066", "Consola Xbox Series X 1TB SSD", "3", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687919443/consola-xbox-series-x-1tb-ssd-consola-xbox-series-x-1tb-ssd_rva7fj.webp", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687919451/consola-xbox-series-x-1tb-ssd-consola-xbox-series-x-1tb-ssd_oqca2v.webp", ""},
                {"PlayStation 5", "4500", "45200", "100000067", "Consola Sony Playstation 5 Disc PS5 825GB SSD", "3", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687919460/consola-sony-playstation-5-disc-ps5-825gb-ssd-consola-sony-playstation-5-disc-ps5-825gb-ssd_e1cdj9.webp", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687919466/consola-sony-playstation-5-disc-ps5-825gb-ssd-consola-sony-playstation-5-disc-ps5-825gb-ssd_dxgkic.webp", ""},
                {"Playstation 4", "4000", "25800", "100000068", "Consola Sony Playstation 4 PS4 Slim 1TB", "3", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687919476/consola-sony-playstation-4-ps4-slim-1tb-consola-sony-playstation-4-ps4-slim-1tb_dwwspr.webp", ""},
                {"Nintendo Switch Neon Mario Kart 8", "950", "26800", "100000069", "Nintendo Switch Neon Mario Kart 8", "3", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687919483/4872120_wlaxes.jpg", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687919490/4872120-1_eshhmx.jpg", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687919497/4872120-2_pd6isl.jpg", ""},
                {"Tablet Lenoco 7 2G + 32G 7306F", "1200", "5100", "100000070", "Tablet Lenoco 7 2G + 32G 7306F", "3", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687919504/4871237_gbbrqm.jpg", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687919509/4871237-1_gz2lv2.jpg", ""},
                {"Mouse Lift Vertical Logitech", "90", "4200", "100000072", "Mouse Lift Vertical Logitech", "3", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687919519/4869426_pl98sj.jpg", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687919526/4869426-1_lcwnyw.jpg", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687919532/4869426-5_ukpnan.jpg", ""},
                {"Pelota Volley", "55", "750", "100000073", "Pelota Volley", "5", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687919539/pelota-volley-pro-naranja-blanco_ttkafe.webp", ""},
                {"Pelota Futbol PUMA", "65", "1300", "100000074", "Pelota Futbol PUMA", "5", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687919543/pelota-play-big-cat-amarillo-negro_nj3aha.webp", ""},
                {"Pelota Basket SPALDING", "1", "1800", "100000075", "Pelota Basket SPALDING", "5", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687919550/pelota-high-ligth-negro-oro_eeslrk.webp", ""},
                {"Pelota Tennis", "60", "410", "100000076", "Pelota Tennis", "5", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687920267/tubo-de-pelotas-de-tenis-tecnifibre-mini-tubo-de-pelotas-de-tenis-tecnifibre-mini_vojwtt.webp", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687920274/tubo-de-pelotas-de-tenis-tecnifibre-mini-tubo-de-pelotas-de-tenis-tecnifibre-mini_xxqxll.webp", ""},
                {"Pelota PingPong", "10", "59", "100000077", "Pelota PingPong", "5", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687920284/pelota-ping-pong-x3uds-25572-pelota-ping-pong-x3uds-25572_z5bsxk.webp", ""},
                {"Mancuerna 15Kg", "15000", "1600", "100000078", "Mancuerna 15Kg", "5", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687920291/hastings-hex-dumbbell-set-15kg_pratov.jpg", ""},
                {"Mancuerna 10Kg", "10000", "1400", "100000079", "Mancuerna 10Kg", "5", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687920297/DUMBBELLS-MANCUERNA-10kg_ltleih.jpg", ""},
                {"Mancuerna 20Kg", "20000", "1800", "100000080", "Mancuerna 20Kg", "5", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687920303/mancuerna-20kg-hexagonal-de-hierro-recubierto-pesas-mancuerna-20kg-hexagonal-de-hierro-recubierto-pesas_ggzamd.jpg", ""},
                {"Caminadora", "8000", "8300", "100000081", "Caminador Eléctrico plegable 1.5 HP", "5", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687920314/2-59_bc560x.jpg", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687920322/3-29-768x768_mhrnho.jpg", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687920328/1-93-768x768_fsrejo.jpg", ""},
                {"Rollos de cocina NOVA", "55", "510", "100000082", "Rollos de cocina NOVA", "2", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687917566/jfnb0dith86ukca1gsi0_iwvw9t.jpg", ""},
                {"Rollos de cocina ELITE", "60", "305", "100000083", "Rollos de cocina ELITE", "2", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687917559/291-36fe2636f8cb83b79415997855867003-640-0_kzimkk.png", ""},
                {"Papel Higiénico ELITE", "15", "197", "100000084", "Papel Higiénico ELITE", "2", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687917552/papel-higienico-elite-hoja-doble-50m-x4-papel-higienico-elite-hoja-doble-50m-x4_ccdy0s.jpg", ""},
                {"Papel Higiénico HIGIENOL", "50", "152", "100000085", "Papel Higiénico HIGIENOL", "2", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687917547/papel-higienico-higienol-30-mt-16-rollos-papel-higienico-higienol-30-mt-16-rollos_quugb7.jpg", ""},
                {"Limpiador líquido FABULOSO", "2000", "180", "100000086", "Limpiador líquido FABULOSO", "2", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687917541/limpiador-liquido-fabuloso-lavanda-2-lt_vnka2x.jpg", ""},
                {"Limpiador líquido CIF", "450", "175", "100000087", "Limpiador líquido CIF", "2", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687917536/6117baf642c0a692391339_ijao2o.png", ""},
                {"Jabón líquido SKIP", "3000", "645", "100000088", "Jabón líquido SKIP", "2", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687917528/jabon-liquido-skip-regular-3-lt_ez45hv.jpg", ""},
                {"Detergente CIF", "500", "145", "100000089", "Detergente CIF", "2", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687917520/cif-active-gel-detergente-lavavajilla-concentrado-lim_C3_B3n-verde_llwxhx.jpg", ""},
                {"Desinfectante LYSOFORM", "360", "228", "100000090", "Desinfectante LYSOFORM", "2", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687917511/47748_ovh9cs.jpg", ""},
                {"Jabón en Barra BULL DOG", "200", "71", "100000091", "Jabón en Barra BULL DOG", "2", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687917505/jabon-bull-dog-en-barra-250-grs_2021-09-03_89910232_gukbz4.jpg", ""},
                {"Jabón DOVE", "90", "76", "100000092", "Jabón DOVE", "2", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687917498/0002525_dove-jabon-blanco-90-grs_600_w5tzhv.jpg", ""},
                {"Jabón SENSUS", "150", "28", "100000093", "Jabón SENSUS", "2", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687917491/561012_tufbuk.jpg", ""},
                {"Jabón PROTEX", "135", "45", "100000094", "Jabón PROTEX", "2", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687917483/P143587-1_fmg5uu.jpg", ""},
                {"Lavandina VIM", "300", "146", "100000095", "Lavandina VIM", "2", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687917476/0002307_vim-lavandina-en-gel-original-300-ml_600_pkygxb.jpg", ""},
                {"Jabón en Polvo DRIVE", "400", "346", "100000096", "Jabón en Polvo DRIVE", "2", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1687917464/detergente-en-polvo-drive-rosas-y-lilas-400-gr-detergente-en-polvo-drive-rosas-y-lilas-400-gr_xieckc.jpg", ""}
        };

        for (String[] val : datos) {
            Producto producto = new Producto();
            producto.setNombre(val[0]);
            producto.setPeso(Integer.parseInt(val[1]));
            producto.setPrecio(BigDecimal.valueOf(Long.parseLong(val[2])));
            producto.setBarcode(val[3]);
            producto.setDescripcion(val[4]);
            Categoria categoria = ManejadorCategoria.getInstance().obtenerCategoria(Long.parseLong(val[5]));
            producto.setCategoria(categoria);

            //Ingreso de imagenes
            int i = 6;
            while (!val[i].isEmpty()) {

                producto.agregarImagenRemota(val[i]);
                i++;
            }

            ManejadorProducto.getInstance().crearProducto(producto);
            ServicioActividad.getInstance().registrarCreacion(producto, usuario);

        }

        cerrarSesion(secreto);
    }

    private CargarProducto iniciarSesion(String email, String passw) {
        icSesion.iniciarSesion(email, passw);
        return this;
    }

    private String obtenerSecreto(String email, String passw) {
        return icSesion.obtenerSesion(email, passw).getSecreto();
    }

    private void cerrarSesion(String secreto) {
        icSesion.cerrarSesion(secreto);
    }
} 
