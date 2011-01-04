class Fixnum 
	ARABIC_TO_ROMAN_NUMERAL = { 10 => 'X', 9 => 'IX', 5 => 'V', 4 => 'IV', 1 => 'I' }.sort.reverse

	def to_roman
		return '' if self == 0
		ARABIC_TO_ROMAN_NUMERAL.each do | arabic_number, roman_numeral |
			return roman_numeral + (self - arabic_number).to_roman if self >= arabic_number
		end
	end
end

describe "Roman number" do
	context "has basic numerals with different values" do
		it "I is equivalent to 1" do 
			1.to_roman.should == 'I'
		end

		it "IV is equivalent to 4" do 
			4.to_roman.should == 'IV'
		end

		it "V is equivalent to 5" do
			5.to_roman.should == 'V'
		end

		it "IX is equivalent to 9" do 
			9.to_roman.should == 'IX'
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

	context "converts even complex examples (to gain more trust in our implementation)" do
		it "XVIII is equivalent to 18" do 
			18.to_roman.should == 'XVIII'
		end

		it "XIX is equivalent to 19" do 
			19.to_roman.should == 'XIX'
		end

		it "XXXVII is equivalent to 37" do 
			37.to_roman.should == 'XXXVII'
		end
	end
end
