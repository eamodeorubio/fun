class Fixnum 
	def to_roman
		return 'X' if self == 10
		return 'V' if self == 5
		'I'
	end
end

describe "Roman number" do
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
