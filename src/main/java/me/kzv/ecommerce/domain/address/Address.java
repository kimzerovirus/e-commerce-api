package me.kzv.ecommerce.domain.address;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import me.kzv.ecommerce.domain.member.Member;
import me.kzv.ecommerce.utils.BooleanToYNConverter;

@Getter
@Entity
public class Address { // 배송지 주소
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long id;

    private String baseAddress; // 기본 주소지
    private String detailAddress; // 상세 주소지
    private String zipcode; // 우편 번호

    private String addressName; // 주소 이름
    @Convert(converter = BooleanToYNConverter.class)
    private Boolean isRecent; // 최근 배송지 여부

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    protected Address(){}

    @Builder
    public Address(Long id, String baseAddress, String detailAddress, String zipcode, String addressName, Boolean isRecent, Member member) {
        this.id = id;
        this.baseAddress = baseAddress;
        this.detailAddress = detailAddress;
        this.zipcode = zipcode;
        this.addressName = addressName;
        this.isRecent = isRecent;
        this.member = member;
    }
}