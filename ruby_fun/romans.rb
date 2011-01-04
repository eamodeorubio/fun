class Fixnum 
	ARABIC_TO_ROMAN_NUMERAL = { 10 => 'X', 5 => 'V', 1 => 'I' }

	def to_roman
		return ARABIC_TO_ROMAN_NUMERAL[self] if ARABIC_TO_ROMAN_NUMERAL[self]
		ARABIC_TO_ROMAN_NUMERAL.each do | arabic_number, roman_numeral |
			return roman_numeral + (self - arabic_number).to_roman if self > arabic_number
		end
	end
end

describe "Roman number" do
	context "has basic numerals with different values" do
		it "I is equivalent to 1" do 
			1.to_roman.should == 'I'
		end

		it "V is equivalent to 5" do
			5.to_roman.should == 'V'
		end

		it "X is equivalent to 10" do
			10.to_roman.should == 'X'
		end
	end

	context "concatenates numerals in descending order until they sum up the desired integer" do
		it "II is equivalent to 2" do
			2.to_roman.should == 'II'
		end 

		it "III is equivalent to 3" do
			3.to_roman.should == 'III'
		end 

		it "VI is equivalent to 6" do
			6.to_roman.should == 'VI'
		end 

		it "XI is equivalent to 11" do
			11.to_roman.should == 'XI'
		end

		it "XV is equivalent to 15" do
			15.to_roman.should == 'XV'
		end 

		it "XX is equivalent to 20" do
			20.to_roman.should == 'XX'
		end 
	end
end
