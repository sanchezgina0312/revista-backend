package co.edu.unbosque.revista.util;

import java.util.Base64;

import org.springframework.stereotype.Component;

@Component
public class EncriptadorUtil {

	public String encriptarTexto(String textoReal) {
		if (textoReal == null || textoReal.isEmpty()) {
			return textoReal;
		}
		return Base64.getEncoder().encodeToString(textoReal.getBytes());
	}

	public String desencriptarTexto(String textoEncriptado) {
		if (textoEncriptado == null || textoEncriptado.isEmpty()) {
			return textoEncriptado;
		}
		byte[] bytesDecodificados = Base64.getDecoder().decode(textoEncriptado);
		return new String(bytesDecodificados);
	}
}
