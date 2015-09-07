package com.grobster.tools;

import java.nio.file.*;
import java.io.*;
import java.util.function.*;

public class ScriptCreator {
	private String programName;
	public static final Path OUTPUT_FILE = Paths.get("jar_script.txt");
	public static final String BEGINNING_JAR_COMMAND = "jar -cvmf manifest.txt";
	public static final String SPACE = " ";
	
	public ScriptCreator(String programName) {
		this.programName = programName;
	}
	
	public String createJarCommand(Path[] directoriesOfClassFiles) {
		StringBuilder sb = new StringBuilder();
		sb.append(BEGINNING_JAR_COMMAND + SPACE + programName + SPACE);
		for (Path directory: directoriesOfClassFiles) {
			if (Files.isDirectory(directory)) {
				File[] classFiles = directory.toFile().listFiles();
				for (File f: classFiles) {
					Path newPath = f.toPath();
					if (newPath.toString().toLowerCase().endsWith(".class")) {
						sb.append(convertToJarCommandPath(newPath) + SPACE);
					}
				}
			}
		}
		
		System.out.println("command " + sb.toString());
		return sb.toString();
	}
	
	public String createJarCommand(Path[] directoriesOfClassFiles, Predicate<Path> p) {
		StringBuilder sb = new StringBuilder();
		sb.append(BEGINNING_JAR_COMMAND + SPACE + programName + SPACE);
		for (Path directory: directoriesOfClassFiles) {
			if (Files.isDirectory(directory)) {
				File[] classFiles = directory.toFile().listFiles();
				for (File f: classFiles) {
					Path newPath = f.toPath();
					if (p.test(newPath)) {
						sb.append(convertToJarCommandPath(newPath) + SPACE);
					}
				}
			}
		}
		
		System.out.println("command " + sb.toString());
		return sb.toString();
	}
	
	public static String createJarCommand(Path[] directoriesOfClassFiles, Predicate<Path> p, String jarName) {
		StringBuilder sb = new StringBuilder();
		sb.append(BEGINNING_JAR_COMMAND + SPACE + jarName + SPACE);
		for (Path directory: directoriesOfClassFiles) {
			if (Files.isDirectory(directory)) {
				File[] classFiles = directory.toFile().listFiles();
				for (File f: classFiles) {
					Path newPath = f.toPath();
					if (p.test(newPath)) {
						sb.append(convertToJarCommandPath(newPath) + SPACE);
					}
				}
			}
		}
		
		System.out.println("command " + sb.toString());
		return sb.toString();
	}
	
	public static String convertToJarCommandPath(Path p) {
		Path shortenedPath = p.subpath(2, p.getNameCount());
		String forwardSlashPath = shortenedPath.toString().replace('\\', '/');	

		return forwardSlashPath;
	}
	
	public static String convertToJarCommandPath(int startIndex, int endingIndex, Path p) {
		Path shortenedPath = p.subpath(startIndex, endingIndex);
		String forwardSlashPath = shortenedPath.toString().replace('\\', '/');	

		return forwardSlashPath;
	}
	
	public static void main(String[] args) {
		Path firstPath = Paths.get("C:\\myprojects\\classes\\com\\grobster\\food");
		Path p2 = Paths.get("C:\\myprojects\\classes\\com\\grobster\\gui");
		final Predicate<Path> predicate = (Path p) -> p.getFileName().toString().toLowerCase().endsWith(".class");
		Path[] myDirs = {firstPath, p2};
		ScriptCreator sc = new ScriptCreator("ScriptCreator.jar");
		sc.createJarCommand(myDirs, predicate);
	}
}