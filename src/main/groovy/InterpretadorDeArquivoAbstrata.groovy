abstract class InterpretadorDeArquivoAbstrata {

	final static String PATH_RESOURCES = '/media/WORK/zeroglosa-workspace/zeroglosa/master-developer-time3/src/main/arquivo-repositorio'

	static File obtenhaArquivoResource(String nomeArquivo) {
		File file = new File("$PATH_RESOURCES/$nomeArquivo")
		return file
	}

	byte[] obtenhaConteudoArquivo(File arquivo){
		return arquivo.bytes
	}

	abstract List<Map<String,String>> obtenhaRegistrosArquivo(String nomeArquivo)
}
