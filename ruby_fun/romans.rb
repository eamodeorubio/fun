class Fixnum 
	def to_roman
		'I'
	end
end

describe "Roman number" do
	it "I is equivalent to 1" do 
		1.to_roman.should == 'I'
	end
end
