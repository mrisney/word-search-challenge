package com.risney.symantec;

import java.util.HashSet;
import java.util.Set;

public class StringPermutationsChallenge {

	/* use Set data structure, avoid duplicates */

	public static Set<String> generatePermutations(String input) {
		Set<String> permutationSet = new HashSet<String>();
		if (input == "")
			return permutationSet;
		Character chr = input.charAt(0);

		if (input.length() > 1) {
			input = input.substring(1);

			Set<String> tmpPermutationSet = generatePermutations(input);

			for (String str : tmpPermutationSet) {
				for (int i = 0; i <= str.length(); i++) {
					permutationSet.add(str.substring(0, i) + chr
							+ str.substring(i));
				}
			}
		} else {
			permutationSet.add(chr + "");
		}
		return permutationSet;
	}

	public static void main(String[] args) {
		String testString = "Symnatec";
		Set<String> permutations = generatePermutations(testString);
		for (String str : permutations) {
			System.out.println(str);
		}
		System.out.println(permutations.size() + " unique  permutations found for '" + testString + "'");

	}
}
